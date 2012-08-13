package com.jimmy3d.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
/**
 * 
 * */
public class Triangle {
	private IntBuffer myVertexBufer;
	private IntBuffer myColorBuffer;
	private ByteBuffer myIndexBuffer;
	int vCount = 0;
	int iCount = 0;
	public float yAngle = 0;
	public float zAngle = 0;

	public Triangle() {
		super();
		vCount = 3;
		final int UNIT_SIZE = 10000;
		int[] vertices = new int[] { -8 * UNIT_SIZE, 6 * UNIT_SIZE, 0,
				-8 * UNIT_SIZE, -6 * UNIT_SIZE, 0, 8 * UNIT_SIZE,
				-6 * UNIT_SIZE, 0

		};
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		myVertexBufer = vbb.asIntBuffer();
		myVertexBufer.put(vertices);
		myVertexBufer.position(0);

		int one = 65535;
		int[] colors = new int[] { one, one, one, 0, one, one, one, 0, one,
				one, one, 0

		};
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		myColorBuffer = cbb.asIntBuffer();
		myColorBuffer.put(colors);
		myColorBuffer.position(0);

		iCount = 3;
		byte[] indices = new byte[] { 0, 1, 2 };
		myIndexBuffer = ByteBuffer.allocateDirect(indices.length);
		myIndexBuffer.put(indices);
		myIndexBuffer.position(0);
	}
    
	public float getyAngle() {
		return yAngle;
	}

	public void setyAngle(float yAngle) {
		this.yAngle = yAngle;
	}

	public float getzAngle() {
		return zAngle;
	}

	public void setzAngle(float zAngle) {
		this.zAngle = zAngle;
	}

	public void drawSelf(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glRotatef(yAngle, 0, 1, 0);
		gl.glRotatef(zAngle, 0, 0, 1);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, myVertexBufer);
		gl.glColorPointer(4, GL10.GL_FIXED, 0, myColorBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, iCount, GL10.GL_UNSIGNED_BYTE,
				myIndexBuffer);
//		gl.glDrawElements(GL10.GL_POINTS, iCount, GL10.GL_UNSIGNED_BYTE,
//				myIndexBuffer);
//		gl.glDrawElements(GL10.GL_LINES, iCount, GL10.GL_UNSIGNED_BYTE,
//				myIndexBuffer);
	}

}
