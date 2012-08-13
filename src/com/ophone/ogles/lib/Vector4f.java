package com.ophone.ogles.lib;

public class Vector4f {
	public float x, y, z, w;
	
	public Vector4f() {
		
	}
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(Vector4f v) {
		set(v.x, v.y, v.z, v.w);
	}
}
