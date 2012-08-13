package com.ophone;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.ophone.ogles.lib.IBufferFactory;
import com.ophone.ogles.lib.Vector3f;
import com.ophone.ogles.lib.Vertex;

/**
 * ������Ⱦ���������
 * @author Yong
 *
 */
public class CoverRenderable {
	// ��Ⱦ���
	FloatBuffer mBufPosition;
	FloatBuffer mBufColor;
	FloatBuffer mBufNormal;
	FloatBuffer mBufTexture;
	// ��������
	ShortBuffer mBufIndicesOpaque;
	ShortBuffer mBufIndicesBlend;
	
	/**
	 * �������漸���塣��������16�����㣬9���ı��Σ���18�������Σ���ɡ�
	 * �������������򣨼�����5��6��9��10��ɵ���������Ϊ��ȫ��͸����
	 * ������������Ϊ��Ե������ʣ�ඥ���alpha����Ϊ��ȫ͸���������ͻ���һ�����뵭����
	 * ����Ч�����������Ӿ�Ө��͸�ĸо�������Ҳһ���̶��Ͼ����˷�����Ч����
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
		//������16�����㣬���ﻹ���������� �ĵ�Ӱ������һ��Ҫ����32��
		Vertex[] pVertices = new Vertex[16 * 2];
		
		//��͸�����ֵ�����
		int[] pIndicesOpaque = new int[12];
		// ͸������
		int[] pIndicesBlend = new int[96];
		// ��ʼ������
		for (int i = 0; i < pVertices.length; i++) {
			pVertices[i] = new Vertex();
		}

		// ���÷����ʼ����������ȸ߶Ⱦ�Ϊ6.0f, ������뵹Ӱ�ľ�����0
		// �����Ҫ��ʾ������ͼƬ�������������޸�ͼƬ����
		float width = 6.0f, height = 6.0f, heightFromMirror = 0.0f;
		float dim = 0.5f; // as uv
		float mfBorderFraction = 0.05f;//���ɱ�Ե���ϵ�����������Ϊ0�ͱ�ʾû�б�Ե����
		float dimLess = 0.5f - (0.5f * mfBorderFraction);
		//���ö���ķ��ߣ���������û�����ù��գ���˲�δ�ر�����
		Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
		//���ö����λ��
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
		
		//�������ж������ɫ�ͷ���
		for (int i = 0; i < 16; i++) {
			//���÷���
			pVertices[i].n.set(normal);
			//���ö���ɫ
			pVertices[i].c.set(1.0f, 1.0f, 1.0f, 0.0f);
			//������������
			pVertices[i].t.x = pVertices[i].p.x + 0.5f;
			pVertices[i].t.y = pVertices[i].p.y + 0.5f;
			//���ö���λ��
			pVertices[i].p.x = pVertices[i].p.x * width;
			pVertices[i].p.y = pVertices[i].p.y * height;
		}
		//�������������4�����㣬����Ϊ��ȫ��͸������alpha = 1.0
		pVertices[5].c.w = 1.0f;
		pVertices[6].c.w = 1.0f;
		pVertices[9].c.w = 1.0f;
		pVertices[10].c.w = 1.0f;

		//��������Ӱ
		for (int row = 0; row < 4; ++row) {
			//���ݾ���ĸ߶ȵ�������ɫ����
			float dark = 1 - ((pVertices[row * 4].p.y / height) + 0.5f);
			dark -= 0.5f;

			for (int col = 0; col < 4; col++) {
				int offset = row * 4 + col;
				//����Ӱ����Ķ��㴹ֱ��ת
				pVertices[offset + 16].set(pVertices[offset]);
				pVertices[offset + 16].p.y = -pVertices[offset + 16].p.y;
				pVertices[offset + 16].p.y -= height + heightFromMirror;
				
				//���õ�Ӱ���漸����Ķ�����ɫ
				pVertices[offset + 16].c.x = dark;
				pVertices[offset + 16].c.y = dark;
				pVertices[offset + 16].c.z = dark;
			}
		}
		
		//��ʼ���������������б�
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

		//�����������Ⱦ������
		mBufPosition = IBufferFactory.newFloatBuffer(32 * 3);
		mBufColor = IBufferFactory.newFloatBuffer(32 * 4);
		mBufNormal = IBufferFactory.newFloatBuffer(32 * 3);
		mBufTexture = IBufferFactory.newFloatBuffer(32 * 2);

		mBufIndicesBlend = IBufferFactory.newShortBuffer(96);
		mBufIndicesOpaque = IBufferFactory.newShortBuffer(12);
		
		//�������
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
		//����Ⱦ����
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBufPosition);
		gl.glNormalPointer(3, GL10.GL_FLOAT, mBufNormal);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mBufColor);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mBufTexture);
	}
	
	/**
	 * ��Ⱦ�������
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// ������Ⱦ�м䲻͸���Ĳ���
		gl.glDrawElements(GL10.GL_TRIANGLES, 12, GL10.GL_UNSIGNED_SHORT,
				mBufIndicesOpaque);

		// ���û�ϲ���
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// ��Ⱦ��͸���ȵķ����Ե
		gl.glDrawElements(GL10.GL_TRIANGLES, 96, GL10.GL_UNSIGNED_SHORT,
				mBufIndicesBlend);
		// ���û��
		gl.glDisable(GL10.GL_BLEND);
	}
}
