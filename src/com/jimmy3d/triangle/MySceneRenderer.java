package com.jimmy3d.triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jimmy3d.shape.Triangle;

import android.opengl.GLSurfaceView;

public class MySceneRenderer implements GLSurfaceView.Renderer {
	Triangle tr = new Triangle();

	public MySceneRenderer() {
		super();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glFrontFace(GL10.GL_CCW);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0.5f, 0, -2.0f);
		tr.drawSelf(gl);
		gl.glTranslatef(-1.0f, 0, 0);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		float ratio = (float) width / height;
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}

}
