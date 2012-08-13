package cover;

public class Vertex {
	Vector3f n;//n //设置顶点的 法线
	Vector3fColor c;//c //设置顶点色 
	Vector3f t;//t  //设置纹理坐标
	Vector3f p;//p //设置顶点位置
     public Vertex(){
    	 
     }
     public void set(Vertex v){
    	 this.n = v.n;
    	 this.c = v.c;
    	 this.t = v.t;
    	 this.p = v.p;
     }
     class Vector3fColor extends Vector3f{
    	 float w;
    	public  Vector3fColor(float red , float green, float blue , float alpha){
    		super(red,green,blue);
    		w = alpha;
    	}
    	public void set(float red , float green, float blue , float alpha){
    		set(red,green,blue);
    		w = alpha;
    	}
     }
}
