package com.ophone.ogles.lib;

import java.nio.FloatBuffer;

public class Matrix4f {
	public static FloatBuffer gFBMatrix = IBufferFactory.newFloatBuffer(16);
	
	public float m00, m01, m02, m03;
	public float m10, m11, m12, m13;
	public float m20, m21, m22, m23;
	public float m30, m31, m32, m33;

	public void setIdentity() {
		this.m00 = 1.0f;
		this.m01 = 0.0f;
		this.m02 = 0.0f;
		this.m03 = 0.0f;

		this.m10 = 0.0f;
		this.m11 = 1.0f;
		this.m12 = 0.0f;
		this.m13 = 0.0f;

		this.m20 = 0.0f;
		this.m21 = 0.0f;
		this.m22 = 1.0f;
		this.m23 = 0.0f;

		this.m30 = 0.0f;
		this.m31 = 0.0f;
		this.m32 = 0.0f;
		this.m33 = 1.0f;
	}

	public final void setTranslation(Vector3f trans) {
		m03 = trans.x;
		m13 = trans.y;
		m23 = trans.z;
	}
	
	public final void setTranslation(float x, float y, float z) {
		m03 = x;
		m13 = y;
		m23 = z;
	}
	
	/**
     * Sets the value of this matrix to a counter clockwise rotation 
     * about the y axis.
     * @param angle the angle to rotate about the Y axis in radians
     */
    public final void rotY(float angle) {
        float sinAngle, cosAngle;

        sinAngle = (float) Math.sin((double) angle);
        cosAngle = (float) Math.cos((double) angle);

        this.m00 = cosAngle;
        this.m01 = (float) 0.0;
        this.m02 = -sinAngle;
        this.m03 = (float) 0.0;

        this.m10 = (float) 0.0;
        this.m11 = (float) 1.0;
        this.m12 = (float) 0.0;
        this.m13 = (float) 0.0;

        this.m20 = sinAngle;
        this.m21 = (float) 0.0;
        this.m22 = cosAngle;
        this.m23 = (float) 0.0;

        this.m30 = (float) 0.0;
        this.m31 = (float) 0.0;
        this.m32 = (float) 0.0;
        this.m33 = (float) 1.0;
    }
	
	public final void set(Matrix4f m1) {
        this.m00 = m1.m00;
        this.m01 = m1.m01;
        this.m02 = m1.m02;
        this.m03 = m1.m03;

        this.m10 = m1.m10;
        this.m11 = m1.m11;
        this.m12 = m1.m12;
        this.m13 = m1.m13;

        this.m20 = m1.m20;
        this.m21 = m1.m21;
        this.m22 = m1.m22;
        this.m23 = m1.m23;

        this.m30 = m1.m30;
        this.m31 = m1.m31;
        this.m32 = m1.m32;
        this.m33 = m1.m33;
    }
	
	public final void mul(Matrix4f m1) {
        float m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33;  // vars for temp result matrix

        m00 = this.m00 * m1.m00 + this.m01 * m1.m10 +
                this.m02 * m1.m20 + this.m03 * m1.m30;
        m01 = this.m00 * m1.m01 + this.m01 * m1.m11 +
                this.m02 * m1.m21 + this.m03 * m1.m31;
        m02 = this.m00 * m1.m02 + this.m01 * m1.m12 +
                this.m02 * m1.m22 + this.m03 * m1.m32;
        m03 = this.m00 * m1.m03 + this.m01 * m1.m13 +
                this.m02 * m1.m23 + this.m03 * m1.m33;

        m10 = this.m10 * m1.m00 + this.m11 * m1.m10 +
                this.m12 * m1.m20 + this.m13 * m1.m30;
        m11 = this.m10 * m1.m01 + this.m11 * m1.m11 +
                this.m12 * m1.m21 + this.m13 * m1.m31;
        m12 = this.m10 * m1.m02 + this.m11 * m1.m12 +
                this.m12 * m1.m22 + this.m13 * m1.m32;
        m13 = this.m10 * m1.m03 + this.m11 * m1.m13 +
                this.m12 * m1.m23 + this.m13 * m1.m33;

        m20 = this.m20 * m1.m00 + this.m21 * m1.m10 +
                this.m22 * m1.m20 + this.m23 * m1.m30;
        m21 = this.m20 * m1.m01 + this.m21 * m1.m11 +
                this.m22 * m1.m21 + this.m23 * m1.m31;
        m22 = this.m20 * m1.m02 + this.m21 * m1.m12 +
                this.m22 * m1.m22 + this.m23 * m1.m32;
        m23 = this.m20 * m1.m03 + this.m21 * m1.m13 +
                this.m22 * m1.m23 + this.m23 * m1.m33;

        m30 = this.m30 * m1.m00 + this.m31 * m1.m10 +
                this.m32 * m1.m20 + this.m33 * m1.m30;
        m31 = this.m30 * m1.m01 + this.m31 * m1.m11 +
                this.m32 * m1.m21 + this.m33 * m1.m31;
        m32 = this.m30 * m1.m02 + this.m31 * m1.m12 +
                this.m32 * m1.m22 + this.m33 * m1.m32;
        m33 = this.m30 * m1.m03 + this.m31 * m1.m13 +
                this.m32 * m1.m23 + this.m33 * m1.m33;

        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }
	
	public final void mul(Matrix4f m1, Matrix4f m2) {
        if (this != m1 && this != m2) {

            this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 +
                    m1.m02 * m2.m20 + m1.m03 * m2.m30;
            this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 +
                    m1.m02 * m2.m21 + m1.m03 * m2.m31;
            this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 +
                    m1.m02 * m2.m22 + m1.m03 * m2.m32;
            this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 +
                    m1.m02 * m2.m23 + m1.m03 * m2.m33;

            this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 +
                    m1.m12 * m2.m20 + m1.m13 * m2.m30;
            this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 +
                    m1.m12 * m2.m21 + m1.m13 * m2.m31;
            this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 +
                    m1.m12 * m2.m22 + m1.m13 * m2.m32;
            this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 +
                    m1.m12 * m2.m23 + m1.m13 * m2.m33;

            this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 +
                    m1.m22 * m2.m20 + m1.m23 * m2.m30;
            this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 +
                    m1.m22 * m2.m21 + m1.m23 * m2.m31;
            this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 +
                    m1.m22 * m2.m22 + m1.m23 * m2.m32;
            this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 +
                    m1.m22 * m2.m23 + m1.m23 * m2.m33;

            this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 +
                    m1.m32 * m2.m20 + m1.m33 * m2.m30;
            this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 +
                    m1.m32 * m2.m21 + m1.m33 * m2.m31;
            this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 +
                    m1.m32 * m2.m22 + m1.m33 * m2.m32;
            this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 +
                    m1.m32 * m2.m23 + m1.m33 * m2.m33;
        } else {
            float m00, m01, m02, m03,
                    m10, m11, m12, m13,
                    m20, m21, m22, m23,
                    m30, m31, m32, m33;  // vars for temp result matrix
            m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
            m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
            m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
            m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;

            m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
            m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
            m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
            m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;

            m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
            m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
            m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
            m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;

            m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
            m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
            m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
            m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;

            this.m00 = m00;
            this.m01 = m01;
            this.m02 = m02;
            this.m03 = m03;
            this.m10 = m10;
            this.m11 = m11;
            this.m12 = m12;
            this.m13 = m13;
            this.m20 = m20;
            this.m21 = m21;
            this.m22 = m22;
            this.m23 = m23;
            this.m30 = m30;
            this.m31 = m31;
            this.m32 = m32;
            this.m33 = m33;
        }
    }
	
	public final void set(Quat4f q1) {
        this.m00 = (1.0f - 2.0f * q1.y * q1.y - 2.0f * q1.z * q1.z);
        this.m10 = (2.0f * (q1.x * q1.y + q1.w * q1.z));
        this.m20 = (2.0f * (q1.x * q1.z - q1.w * q1.y));

        this.m01 = (2.0f * (q1.x * q1.y - q1.w * q1.z));
        this.m11 = (1.0f - 2.0f * q1.x * q1.x - 2.0f * q1.z * q1.z);
        this.m21 = (2.0f * (q1.y * q1.z + q1.w * q1.x));

        this.m02 = (2.0f * (q1.x * q1.z + q1.w * q1.y));
        this.m12 = (2.0f * (q1.y * q1.z - q1.w * q1.x));
        this.m22 = (1.0f - 2.0f * q1.x * q1.x - 2.0f * q1.y * q1.y);

        this.m03 = (float) 0.0;
        this.m13 = (float) 0.0;
        this.m23 = (float) 0.0;

        this.m30 = (float) 0.0;
        this.m31 = (float) 0.0;
        this.m32 = (float) 0.0;
        this.m33 = (float) 1.0;
    }
	
	public final void invTransform(Vector3f point, Vector3f pointOut) {
        Vector3f tmp = new Vector3f();
        tmp.x = point.x - m03;
        tmp.y = point.y - m13;
        tmp.z = point.z - m23;

        //transform normal
        invTransformRotate(tmp, pointOut);
    }
	
	public final void invTransformRotate(Vector3f normal, Vector3f normalOut) {
        float x, y;
        x = m00 * normal.x + m10 * normal.y + m20 * normal.z;
        y = m01 * normal.x + m11 * normal.y + m21 * normal.z;
        normalOut.z = m02 * normal.x + m12 * normal.y + m22 * normal.z;
        normalOut.x = x;
        normalOut.y = y;
    }
	
	public final void transform(Vector3f point, Vector3f pointOut) {
        float x, y;
        x = m00 * point.x + m01 * point.y + m02 * point.z + m03;
        y = m10 * point.x + m11 * point.y + m12 * point.z + m13;
        pointOut.z = m20 * point.x + m21 * point.y + m22 * point.z + m23;
        pointOut.x = x;
        pointOut.y = y;
    }
	
	/**
     * Sets the value of this matrix to its transpose in place.
     */
    public final void transpose() {
        float temp;

        temp = this.m10;
        this.m10 = this.m01;
        this.m01 = temp;

        temp = this.m20;
        this.m20 = this.m02;
        this.m02 = temp;

        temp = this.m30;
        this.m30 = this.m03;
        this.m03 = temp;

        temp = this.m21;
        this.m21 = this.m12;
        this.m12 = temp;

        temp = this.m31;
        this.m31 = this.m13;
        this.m13 = temp;

        temp = this.m32;
        this.m32 = this.m23;
        this.m23 = temp;
    }
    
    public FloatBuffer AsFloatBuffer() {
        gFBMatrix.position(0);
        fillFloatBuffer(gFBMatrix);
        return gFBMatrix;
    }
    
    /**
     * get float buffer
     * coloum major
     * @param buffer
     */
    public final void fillFloatBuffer(FloatBuffer buffer) {
        buffer.position(0);
        buffer.put(m00);
        buffer.put(m10);
        buffer.put(m20);
        buffer.put(m30);

        buffer.put(m01);
        buffer.put(m11);
        buffer.put(m21);
        buffer.put(m31);

        buffer.put(m02);
        buffer.put(m12);
        buffer.put(m22);
        buffer.put(m32);

        buffer.put(m03);
        buffer.put(m13);
        buffer.put(m23);
        buffer.put(m33);

        buffer.position(0);
    }
    
    private static Vector3f tmpF = new Vector3f(), tmpUp = new Vector3f(), tmpS = new Vector3f(), tmpT = new Vector3f();
    private static Matrix4f tmpMat = new Matrix4f();
    public static void gluLookAt(Vector3f eye, Vector3f center, Vector3f up, Matrix4f out) {
    	tmpF.x = center.x - eye.x;
    	tmpF.y = center.y - eye.y;
    	tmpF.z = center.z - eye.z;
    	
    	tmpF.normalize();
    	
    	tmpUp.set(up);
    	tmpUp.normalize();
    	
    	tmpS.cross(tmpF, tmpUp);
    	tmpT.cross(tmpS, tmpF);
    	
    	out.m00 = tmpS.x;
    	out.m10 = tmpT.x;
    	out.m20 = -tmpF.x;
    	out.m30 = 0;
    	
    	out.m01 = tmpS.y;
    	out.m11 = tmpT.y;
    	out.m21 = -tmpF.y;
    	out.m31 = 0;
    	
    	out.m02 = tmpS.z;
    	out.m12 = tmpT.z;
    	out.m22 = -tmpF.z;
    	out.m32 = 0;
    	
    	out.m03 = 0;
    	out.m13 = 0;
    	out.m23 = 0;
    	out.m33 = 1;
    	
    	tmpMat.setIdentity();
    	tmpMat.setTranslation(-eye.x, -eye.y, -eye.z);
    	
    	out.mul(tmpMat);
    }
}
