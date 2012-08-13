package com.ophone;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;

import com.jimmy3d.R;
import com.ophone.ogles.lib.*;

public class OPhone3DCoverShowRenderer implements GLSurfaceView.Renderer {
	// 用于标识封面位置的常量。
	// 表示左0，左1，左2，，，，中间，，，右0，右1，右2，，，右5
	private static final int eLeft = 0, eLeft1 = 1, eLeft2 = 2, eLeft3 = 3,
			eLeft4 = 4, eLeft5 = 5, eFront = 6, eRight = 7, eRight1 = 8,
			eRight2 = 9, eRight3 = 10, eRight4 = 11, eRight5 = 12,
			eCoverMeshsNo = 13;
	// 显示16个封面
	private static final int gCoverCount = 16;
	/**
	 * 封面渲染对象
	 */
	private CoverRenderable mCoverRenderable;

	// 封面切换动画相关系数
	int miLerpDir;
	float mfLerp;
	long miTimePrev;
	float mfCyclesPerSecond = 10.0f;// 控制滑动速度
	int miCoverIndex;
	boolean m_bGoRight;

	// 纹理相关
	int[] mpTexIDs;
	/**
	 * 纹理资源ID
	 */
	private static final int[] gpTexRIDs = { R.drawable.album1,
			R.drawable.album2, R.drawable.album3, R.drawable.album4,
			R.drawable.album5, R.drawable.album6, R.drawable.ophone,
			R.drawable.album7, R.drawable.album8, R.drawable.album9,
			R.drawable.album10, R.drawable.album11, R.drawable.album12,
			R.drawable.album13, R.drawable.album14, R.drawable.album15,
			R.drawable.album16 };

	private Context mContext;

	public OPhone3DCoverShowRenderer(Context context) {
		mContext = context;
	}

	/**
	 * 主渲染部分
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// 清屏
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 禁用背面剪裁
		gl.glDisable(GL10.GL_CULL_FACE);

		// 启用客户端响应状态
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		mCoverRenderable.bind(gl);
		
		// 渲染封面
		// 根据不同运动趋势，决定不同渲染次序
		if (mfLerp < -0.5) {
			drawLeftCovers(gl);
			drawInPosition(gl, eFront, mfLerp, miCoverIndex);
			drawRightCovers(gl);
		} else if (mfLerp > 0.5) {
			drawRightCovers(gl);
			drawInPosition(gl, eFront, mfLerp, miCoverIndex);
			drawLeftCovers(gl);
		} else {
			drawRightCovers(gl);
			drawLeftCovers(gl);
			drawInPosition(gl, eFront, mfLerp, miCoverIndex);
		}

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	/**
	 * 渲染左侧的封面
	 * 
	 * @param gl
	 */
	private void drawLeftCovers(GL10 gl) {
		int startIdx = 0;
		if (miLerpDir == 0) {
			startIdx = 1;
		}
		for (int i = startIdx; i < eFront; ++i) {
			drawInPosition(gl, i, mfLerp, miCoverIndex);
		}
	}

	/**
	 * 渲染右侧的封面
	 * 
	 * @param gl
	 */
	private void drawRightCovers(GL10 gl) {
		for (int i = eCoverMeshsNo - 1; i > eFront; --i) {
			drawInPosition(gl, i, mfLerp, miCoverIndex);
		}
	}

	// 临时的全局性的变量，避免主循环中频繁的new操作
	Matrix4f matTrans = new Matrix4f(), matRotation = new Matrix4f(),
			matTemp = new Matrix4f();
	Vector3f pos = new Vector3f();

	/**
	 * 渲染某个索引位置的封面
	 * 
	 * @param gl
	 * @param index
	 *            - 封面索引，例如左4，左1，右1之类的
	 * @param queueLerp
	 *            - 插值比例
	 * @param coverIndex
	 *            - 当前封面基准索引
	 */
	private void drawInPosition(GL10 gl, int index, float queueLerp,
			int coverIndex) {
		gl.glPushMatrix();
		float angle = 0.0f;
		float backgroundPosition = -8.0f;
		float backgroundAngle = (float) (Math.PI / 2.5f);
		float distInQueue = 3.0f;

		queueLerp = queueLerp + index;
		coverIndex += index;

		if (coverIndex >= gCoverCount) {
			coverIndex -= gCoverCount;
		}
		if (coverIndex < 0) {
			coverIndex += gCoverCount;
		}

		pos.zero();
		pos.x = (queueLerp - eFront) * distInQueue;
		if (queueLerp > eFront - 1 && queueLerp < eFront + 1) {
			float lerpAbs = Math.abs(queueLerp - eFront);
			pos.z = backgroundPosition * lerpAbs;
			angle = backgroundAngle * (queueLerp - eFront);
			pos.x += 2.0f * (queueLerp - eFront);
		} else {
			if (queueLerp - eFront < 0) {
				angle = -backgroundAngle;
			} else {
				angle = backgroundAngle;
			}
			pos.z = (backgroundPosition);
			if (queueLerp - eFront > 0) {
				pos.x += (2.0f);
			} else {
				pos.x -= (2.0f);
			}
		}

		{
			// 构造平移矩阵
			matTrans.setIdentity();
			matTrans.setTranslation(pos);
			// 构造旋转矩阵，仅仅绕着Y轴旋转
			matRotation.setIdentity();
			matRotation.rotY(angle);

			matTemp.setIdentity();
			matTemp.mul(matTrans, matRotation);

			// 设置模型视图矩阵
			gl.glMultMatrixf(matTemp.AsFloatBuffer());
		}

		// 启用纹理
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mpTexIDs[coverIndex]);

		// 渲染封面
		mCoverRenderable.draw(gl);
		gl.glPopMatrix();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		updateInput();
		draw(gl);
	}

	/**
	 * 响应按键操作
	 */
	private void updateInput() {
		// 根据时间进行插值
		long iTime = SystemClock.uptimeMillis();
		long iDeltaTime = iTime - miTimePrev;
		miTimePrev = iTime;

		mfLerp += (iDeltaTime * .0001f) * mfCyclesPerSecond * miLerpDir;

		if (miLerpDir != 0 && (mfLerp >= 1.0 || mfLerp <= -1.0)) {
			if (miLerpDir < 0) {
				miCoverIndex++;
				if (miCoverIndex > gCoverCount) {
					miCoverIndex = 1;
				}
			} else {
				miCoverIndex--;
				if (miCoverIndex < 0) {
					miCoverIndex = gCoverCount - 1;
				}
			}
			miLerpDir = 0;
			mfLerp = 0;
		}
	}

	/**
	 * 向左滑动
	 */
	public void slideLeft() {
		if (miLerpDir != 0) {
			return;
		}
		miLerpDir = 1;
	}

	/**
	 * 向右滑动
	 */
	public void slideRight() {
		if (miLerpDir != 0) {
			return;
		}
		miLerpDir = -1;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// 设置视口
		gl.glViewport(0, 0, width, height);

		// 设置投影矩阵
		float ratio = (float) width / height;// 屏幕宽高比
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, ratio, 1, 5000);
		// 每次修改完GL_PROJECTION后，最好将当前矩阵模型设置回GL_MODELVIEW
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// 设置相机从(0, -3, 18)看向(0, -3, 0)
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0.0f, -3.0f, 18, 0, -3, 0, 0, 1, 0);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// 全局性设置
		gl.glDisable(GL10.GL_DITHER);

		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// 设置清屏背景颜色
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		// 设置着色模型为平滑着色
		gl.glShadeModel(GL10.GL_SMOOTH);

		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);

		// 禁用光照
		gl.glDisable(GL10.GL_LIGHTING);

		mCoverRenderable = new CoverRenderable();
		mCoverRenderable.createCover();

		// 载入纹理
		mpTexIDs = new int[gpTexRIDs.length];
		for (int i = 0; i < mpTexIDs.length; i++) {
			// 载入纹理时，由于需要和顶点色共同作用，因此需要将纹理模式设置为MODULATE
			mpTexIDs[i] = TextureFactory.getTexture(mContext, gl, gpTexRIDs[i],
					GL10.GL_MODULATE);
		}
	}
}
