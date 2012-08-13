package com.ophone;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.ophone.ogles.lib.IBufferFactory;
import com.ophone.ogles.lib.Vector3f;
import com.ophone.ogles.lib.Vertex;

/**
 * 封面渲染几何体对象
 * @author Yong
 *
 */
public class CoverRenderable {
	// 渲染相关
	FloatBuffer mBufPosition;
	FloatBuffer mBufColor;
	FloatBuffer mBufNormal;
	FloatBuffer mBufTexture;
	// 索引缓存
	ShortBuffer mBufIndicesOpaque;
	ShortBuffer mBufIndicesBlend;
	
	/**
	 * 创建封面几何体。几何体由16个顶点，9个四边形（即18个三角形）组成。
	 * 将封面中心区域（即顶点5，6，9，10组成的区域）设置为完全不透明，
	 * 其他的区域都作为边缘，并将剩余顶点的alpha设置为完全透明，这样就会有一个淡入淡出的
	 * 过渡效果。可以增加晶莹剔透的感觉，另外也一定程度上具有了反走样效果。
	 *  0--1------2--3
	 *	|  |      |  |
	 *	4--5------6--7
	 *	|  |      |  |
	 *	|  |      |  |
	 *	|  |      |  |
	 *	8--9-----10--11
	 *	|  |      |  |
	 *	12-13----14--15
	 */
	public void createCover() {
		//封面有16个顶点，这里还包括它自身 的倒影，所以一共要创建32个
		Vertex[] pVertices = new Vertex[16 * 2];
		
		//不透明部分的索引
		int[] pIndicesOpaque = new int[12];
		// 透明部分
		int[] pIndicesBlend = new int[96];
		// 初始化操作
		for (int i = 0; i < pVertices.length; i++) {
			pVertices[i] = new Vertex();
		}

		// 设置封面初始化参数，宽度高度均为6.0f, 封面距离倒影的距离是0
		// 如果需要显示宽屏的图片，可以在这里修改图片比例
		float width = 6.0f, height = 6.0f, heightFromMirror = 0.0f;
		float dim = 0.5f; // as uv
		float mfBorderFraction = 0.05f;//过渡边缘宽度系数，如果设置为0就表示没有边缘过渡
		float dimLess = 0.5f - (0.5f * mfBorderFraction);
		//设置顶点的法线，这里由于没有启用光照，因此并未特别设置
		Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
		//设置顶点的位置
		pVertices[0].p.set(-dim, dim, 0);
		pVertices[1].p.set(-dimLess, dim, 0);
		pVertices[2].p.set(dimLess, dim, 0);
		pVertices[3].p.set(dim, dim, 0);
		pVertices[4].p.set(-dim, dimLess, 0);
		pVertices[5].p.set(-dimLess, dimLess, 0);
		pVertices[6].p.set(dimLess, dimLess, 0);
		pVertices[7].p.set(dim, -dimLess, 0);
		pVertices[8].p.set(-dim, -dimLess, 0);
		pVertices[9].p.set(-dimLess, -dimLess, 0);
		pVertices[10].p.set(dimLess, -dimLess, 0);
		pVertices[11].p.set(dim, -dimLess, 0);
		pVertices[12].p.set(-dim, -dim, 0);
		pVertices[13].p.set(-dimLess, -dim, 0);
		pVertices[14].p.set(dimLess, -dim, 0);
		pVertices[15].p.set(dim, -dim, 0);
		
		//设置所有顶点的颜色和法线
		for (int i = 0; i < 16; i++) {
			//设置法线
			pVertices[i].n.set(normal);
			//设置顶点色
			pVertices[i].c.set(1.0f, 1.0f, 1.0f, 0.0f);
			//设置纹理坐标
			pVertices[i].t.x = pVertices[i].p.x + 0.5f;
			pVertices[i].t.y = pVertices[i].p.y + 0.5f;
			//设置顶点位置
			pVertices[i].p.x = pVertices[i].p.x * width;
			pVertices[i].p.y = pVertices[i].p.y * height;
		}
		//对于中心区域的4个顶点，设置为完全不透明，即alpha = 1.0
		pVertices[5].c.w = 1.0f;
		pVertices[6].c.w = 1.0f;
		pVertices[9].c.w = 1.0f;
		pVertices[10].c.w = 1.0f;

		//创建镜像倒影
		for (int row = 0; row < 4; ++row) {
			//根据镜像的高度调整顶点色明暗
			float dark = 1 - ((pVertices[row * 4].p.y / height) + 0.5f);
			dark -= 0.5f;

			for (int col = 0; col < 4; col++) {
				int offset = row * 4 + col;
				//将倒影封面的顶点垂直翻转
				pVertices[offset + 16].set(pVertices[offset]);
				pVertices[offset + 16].p.y = -pVertices[offset + 16].p.y;
				pVertices[offset + 16].p.y -= height + heightFromMirror;
				
				//设置倒影封面几何体的顶点颜色
				pVertices[offset + 16].c.x = dark;
				pVertices[offset + 16].c.y = dark;
				pVertices[offset + 16].c.z = dark;
			}
		}
		
		//开始构造三角形索引列表
		int i32NumOpaque = 0;
		int i32NumBlend = 0;

		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 3; ++col) {
				int start = (row * 4) + col;
				if (row == 1 && col == 1) {
					pIndicesOpaque[i32NumOpaque++] = start + 1;
					pIndicesOpaque[i32NumOpaque++] = start;
					pIndicesOpaque[i32NumOpaque++] = start + 4;
					pIndicesOpaque[i32NumOpaque++] = start + 1;
					pIndicesOpaque[i32NumOpaque++] = start + 4;
					pIndicesOpaque[i32NumOpaque++] = start + 5;
				} else {
					pIndicesBlend[i32NumBlend++] = start + 1;
					pIndicesBlend[i32NumBlend++] = start;
					pIndicesBlend[i32NumBlend++] = start + 4;
					pIndicesBlend[i32NumBlend++] = start + 1;
					pIndicesBlend[i32NumBlend++] = start + 4;
					pIndicesBlend[i32NumBlend++] = start + 5;
				}
			}
		}

		pIndicesBlend[0] = 1;
		pIndicesBlend[1] = 0;
		pIndicesBlend[2] = 5;
		pIndicesBlend[3] = 0;
		pIndicesBlend[4] = 4;
		pIndicesBlend[5] = 5;

		pIndicesBlend[42] = 11;
		pIndicesBlend[43] = 10;
		pIndicesBlend[44] = 15;
		pIndicesBlend[45] = 10;
		pIndicesBlend[46] = 14;
		pIndicesBlend[47] = 15;

		for (int i = 0; i < 48; i++) {
			if (i < 6) {
				pIndicesOpaque[i + 6] = pIndicesOpaque[i] + 16;
			}
			pIndicesBlend[i + 48] = pIndicesBlend[i] + 16;
		}

		//创建并填充渲染用数据
		mBufPosition = IBufferFactory.newFloatBuffer(32 * 3);
		mBufColor = IBufferFactory.newFloatBuffer(32 * 4);
		mBufNormal = IBufferFactory.newFloatBuffer(32 * 3);
		mBufTexture = IBufferFactory.newFloatBuffer(32 * 2);

		mBufIndicesBlend = IBufferFactory.newShortBuffer(96);
		mBufIndicesOpaque = IBufferFactory.newShortBuffer(12);
		
		//填充数据
		for (int i = 0; i < 32; i++) {
			Vertex v = pVertices[i];
			IBufferFactory.fillBuffer(mBufPosition, v.p);
			IBufferFactory.fillBuffer(mBufColor, v.c);
			IBufferFactory.fillBuffer(mBufNormal, v.n);
			IBufferFactory.fillBuffer(mBufTexture, v.t, 2);
		}

		IBufferFactory.fillBuffer(mBufIndicesBlend, pIndicesBlend);
		IBufferFactory.fillBuffer(mBufIndicesOpaque, pIndicesOpaque);

		mBufPosition.position(0);
		mBufColor.position(0);
		mBufNormal.position(0);
		mBufTexture.position(0);

		mBufIndicesBlend.position(0);
		mBufIndicesOpaque.position(0);
	}
	
	public void bind(GL10 gl) {
		//绑定渲染数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBufPosition);
		gl.glNormalPointer(3, GL10.GL_FLOAT, mBufNormal);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mBufColor);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mBufTexture);
	}
	
	/**
	 * 渲染封面对象
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// 首先渲染中间不透明的部分
		gl.glDrawElements(GL10.GL_TRIANGLES, 12, GL10.GL_UNSIGNED_SHORT,
				mBufIndicesOpaque);

		// 启用混合操作
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// 渲染带透明度的封面边缘
		gl.glDrawElements(GL10.GL_TRIANGLES, 96, GL10.GL_UNSIGNED_SHORT,
				mBufIndicesBlend);
		// 禁用混合
		gl.glDisable(GL10.GL_BLEND);
	}
}
