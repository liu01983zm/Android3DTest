package cover;

public class Vector3f {
    float x; float y; float z;
    public Vector3f(){
    	
    }
    public Vector3f(float x,float y, float z){
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }
    public void set(float x,float y, float z){
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }
    public void set(Vector3f vector){
    	this.x = vector.x;
    	this.y = vector.y;
    	this.z = vector.z;
    }
}
