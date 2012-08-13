package com.jimmy3d.shape;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
/**
 * http://blog.csdn.net/dlnuchunge/article/details/6895242
 * ���Ҫ֪��GLSurfaceview����û����2D�����drawCircle(x,y,r,paint);�ĺ��������Ե��뵽�����ķ����ˡ�
 * ��Բ���Ǹ���R*R=R*RCOS^2+R*RSIN^2;ѭ��N�Σ������Ϳ��Եõ�N���㣬���֮�䶼��ֱ�ߣ����Ե�Խ�࣬ԲԽԲ��
 * */
public class DrawCircle {
	private FloatBuffer myVertexBuffer;//�������껺�� 
	 
	 int vCount;//��������
	 
	 float length;//Բ������
	 float circle_radius;//Բ�ػ��뾶
	 float degreespan;  //Բ�ػ�ÿһ�ݵĶ�����С 
	 int col;//Բ������
	 
	 public float mAngleX;
	 public float mAngleY;
	 public float mAngleZ;
	 //final int UNIT_SIZE=2000;//10000
	 public DrawCircle(float length,float circle_radius,float degreespan,int col)
	 {
	  this.circle_radius=circle_radius;
	  this.length=length;
	  this.col=col;
	  this.degreespan=degreespan;
	 
	  
	  float collength=(float)length/col;//Բ��ÿ����ռ�ĳ���
	  
	  ArrayList<Float> val=new ArrayList<Float>();//�������б�
	  
	  for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//ѭ����
	  {
	   int j=0;
	   //for(int j=0;j<col;j++)//ѭ����
	   {
	    float y1 = (float)0.0f;//(UNIT_SIZE*circle_radius*(j*collength+length/2));
	    float x1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
	    //(float) (UNIT_SIZE*circle_radius*Math.sin(Math.toRadians(circle_degree)))
	    float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
	    
	    if(circle_degree==180.0f){
	    	System.out.println("circle x="+x1+"\t y="+y1+"\t z="+z1);
	    }
	    
	    val.add(x1);val.add(y1);val.add(z1);//ÿ������������ȷ������6���ߣ���12�����㡣
	   }
	  }
	   
	  vCount=val.size()/3;//ȷ����������
	  
	  //����
	  float[] vertexs=new float[vCount*3];
	  for(int i=0;i<vCount*3;i++)
	  {
	   vertexs[i]=val.get(i);
	  }
	  ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
	  vbb.order(ByteOrder.nativeOrder());
	  myVertexBuffer=vbb.asFloatBuffer();
	  myVertexBuffer.put(vertexs);
	  myVertexBuffer.position(0);
	 }
	 
	 public void drawSelf(GL10 gl)
	 {
	  gl.glRotatef(mAngleX, 1, 0, 0);//��ת
	  gl.glRotatef(mAngleY, 0, 1, 0);
	  gl.glRotatef(mAngleZ, 0, 0, 1);
	  
	  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//�򿪶��㻺��
	  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//ָ�����㻺��
	  
	  gl.glColor4f(0, 0, 0, 0);//���û�����Ϊ��ɫ
	  gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, vCount);//����ͼ��
	  
	  gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	 }
	 
	 public void rotate(float angleX,float angleY,float angleZ){
		 mAngleX += angleX;
		 mAngleY += angleY;
		 mAngleZ += angleZ;
	 }
}
