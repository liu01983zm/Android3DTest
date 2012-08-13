package cover;

public class Vertex {
	Vector3f n;//n //���ö���� ����
	Vector3fColor c;//c //���ö���ɫ 
	Vector3f t;//t  //������������
	Vector3f p;//p //���ö���λ��
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
