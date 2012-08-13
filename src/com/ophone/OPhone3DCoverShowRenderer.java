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
	// ���ڱ�ʶ����λ�õĳ�����
	// ��ʾ��0����1����2���������м䣬������0����1����2��������5
	private static final int eLeft = 0, eLeft1 = 1, eLeft2 = 2, eLeft3 = 3,
			eLeft4 = 4, eLeft5 = 5, eFront = 6, eRight = 7, eRight1 = 8,
			eRight2 = 9, eRight3 = 10, eRight4 = 11, eRight5 = 12,
			eCoverMeshsNo = 13;
	// ��ʾ16������
	private static final int gCoverCount = 16;
	/**
	 * ������Ⱦ����
	 */
	private CoverRenderable mCoverRenderable;

	// �����л��������ϵ��
	int miLerpDir;
	float mfLerp;
	long miTimePrev;
	float mfCyclesPerSecond = 10.0f;// ���ƻ����ٶ�
	int miCoverIndex;
	boolean m_bGoRight;

	// �������
	int[] mpTexIDs;
	/**
	 * ������ԴID
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
	 * ����Ⱦ����
	 * 
	 * @param gl
	 */
	public void draw(GL10 gl) {
		// ����
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ���ñ������
		gl.glDisable(GL10.GL_CULL_FACE);

		// ���ÿͻ�����Ӧ״̬
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		mCoverRenderable.bind(gl);
		
		// ��Ⱦ����
		// ���ݲ�ͬ�˶����ƣ�������ͬ��Ⱦ����
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
	 * ��Ⱦ���ķ���
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
	 * ��Ⱦ�Ҳ�ķ���
	 * 
	 * @param gl
	 */
	private void drawRightCovers(GL10 gl) {
		for (int i = eCoverMeshsNo - 1; i > eFront; --i) {
			drawInPosition(gl, i, mfLerp, miCoverIndex);
		}
	}

	// ��ʱ��ȫ���Եı�����������ѭ����Ƶ����new����
	Matrix4f matTrans = new Matrix4f(), matRotation = new Matrix4f(),
			matTemp = new Matrix4f();
	Vector3f pos = new Vector3f();

	/**
	 * ��Ⱦĳ������λ�õķ���
	 * 
	 * @param gl
	 * @param index
	 *            - ����������������4����1����1֮���
	 * @param queueLerp
	 *            - ��ֵ����
	 * @param coverIndex
	 *            - ��ǰ�����׼����
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
			// ����ƽ�ƾ���
			matTrans.setIdentity();
			matTrans.setTranslation(pos);
			// ������ת���󣬽�������Y����ת
			matRotation.setIdentity();
			matRotation.rotY(angle);

			matTemp.setIdentity();
			matTemp.mul(matTrans, matRotation);

			// ����ģ����ͼ����
			gl.glMultMatrixf(matTemp.AsFloatBuffer());
		}

		// ��������
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mpTexIDs[coverIndex]);

		// ��Ⱦ����
		mCoverRenderable.draw(gl);
		gl.glPopMatrix();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		updateInput();
		draw(gl);
	}

	/**
	 * ��Ӧ��������
	 */
	private void updateInput() {
		// ����ʱ����в�ֵ
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
	 * ���󻬶�
	 */
	public void slideLeft() {
		if (miLerpDir != 0) {
			return;
		}
		miLerpDir = 1;
	}

	/**
	 * ���һ���
	 */
	public void slideRight() {
		if (miLerpDir != 0) {
			return;
		}
		miLerpDir = -1;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// �����ӿ�
		gl.glViewport(0, 0, width, height);

		// ����ͶӰ����
		float ratio = (float) width / height;// ��Ļ��߱�
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, ratio, 1, 5000);
		// ÿ���޸���GL_PROJECTION����ý���ǰ����ģ�����û�GL_MODELVIEW
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// ���������(0, -3, 18)����(0, -3, 0)
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0.0f, -3.0f, 18, 0, -3, 0, 0, 1, 0);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		// ȫ��������
		gl.glDisable(GL10.GL_DITHER);

		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// ��������������ɫ
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		// ������ɫģ��Ϊƽ����ɫ
		gl.glShadeModel(GL10.GL_SMOOTH);

		// ������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);

		// ���ù���
		gl.glDisable(GL10.GL_LIGHTING);

		mCoverRenderable = new CoverRenderable();
		mCoverRenderable.createCover();

		// ��������
		mpTexIDs = new int[gpTexRIDs.length];
		for (int i = 0; i < mpTexIDs.length; i++) {
			// ��������ʱ��������Ҫ�Ͷ���ɫ��ͬ���ã������Ҫ������ģʽ����ΪMODULATE
			mpTexIDs[i] = TextureFactory.getTexture(mContext, gl, gpTexRIDs[i],
					GL10.GL_MODULATE);
		}
	}
}
