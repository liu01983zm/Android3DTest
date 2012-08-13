package com.jimmy3d.shape;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
/**
 * http://blog.csdn.net/dlnuchunge/article/details/6895242
 * 大家要知道GLSurfaceview里面没有像2D里面的drawCircle(x,y,r,paint);的函数，所以得想到其他的方法了。
 * 画圆就是根据R*R=R*RCOS^2+R*RSIN^2;循环N次，这样就可以得到N个点，点点之间都是直线，所以点越多，圆越圆。
 * */
public class DrawCircle {
	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
	 
	 int vCount;//顶点数量
	 
	 float length;//圆柱长度
	 float circle_radius;//圆截环半径
	 float degreespan;  //圆截环每一份的度数大小 
	 int col;//圆柱块数
	 
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
	 
	  
	  float collength=(float)length/col;//圆柱每块所占的长度
	  
	  ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
	  
	  for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
	  {
	   int j=0;
	   //for(int j=0;j<col;j++)//循环列
	   {
	    float y1 = (float)0.0f;//(UNIT_SIZE*circle_radius*(j*collength+length/2));
	    float x1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
	    //(float) (UNIT_SIZE*circle_radius*Math.sin(Math.toRadians(circle_degree)))
	    float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
	    
	    if(circle_degree==180.0f){
	    	System.out.println("circle x="+x1+"\t y="+y1+"\t z="+z1);
	    }
	    
	    val.add(x1);val.add(y1);val.add(z1);//每条线两个顶点确定，有6条线，共12个顶点。
	   }
	  }
	   
	  vCount=val.size()/3;//确定顶点数量
	  
	  //顶点
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
	  gl.glRotatef(mAngleX, 1, 0, 0);//旋转
	  gl.glRotatef(mAngleY, 0, 1, 0);
	  gl.glRotatef(mAngleZ, 0, 0, 1);
	  
	  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
	  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
	  
	  gl.glColor4f(0, 0, 0, 0);//设置绘制线为黑色
	  gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, vCount);//绘制图像
	  
	  gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	 }
	 
	 public void rotate(float angleX,float angleY,float angleZ){
		 mAngleX += angleX;
		 mAngleY += angleY;
		 mAngleZ += angleZ;
	 }
}
