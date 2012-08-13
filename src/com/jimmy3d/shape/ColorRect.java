package com.jimmy3d.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * http://blog.csdn.net/dlnuchunge/article/details/6912044
 * @author Jimmy
 *
 */
public class ColorRect {
	private FloatBuffer   mVertexBuffer;//�����������ݻ���  
    private IntBuffer   mColorBuffer,mColorBuffer1,mColor;//������ɫ���ݻ���  
    int vCount=0;//��������  
    public static boolean flag=true;  
    public ColorRect(float width,float height)  
    {  
        //�����������ݵĳ�ʼ��================begin============================  
        vCount=6;  
        final float UNIT_SIZE=1.0f;  
        float vertices[]=new float[]  
        {  
            0,0,0,  
            width*UNIT_SIZE,height*UNIT_SIZE,0,  
            -width*UNIT_SIZE,height*UNIT_SIZE,0,  
            -width*UNIT_SIZE,-height*UNIT_SIZE,0,  
            width*UNIT_SIZE,-height*UNIT_SIZE,0,  
            width*UNIT_SIZE,height*UNIT_SIZE,0  
        };  
          
        //���������������ݻ���  
        //vertices.length*4����Ϊһ�������ĸ��ֽ�  
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);  
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��  
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���  
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������  
        mVertexBuffer.position(0);//���û�������ʼλ��  
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer  
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������  
        //�����������ݵĳ�ʼ��================end============================  
          
        //������ɫ���ݵĳ�ʼ��================begin============================  
        final int one = 65535;  
        int colors[]=new int[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA  
        {  
                one,one,one,0,  
                0,0,one,0,  
                0,0,one,0,  
                0,0,one,0,  
                0,0,one,0,            
                0,0,one,0,  
        };  
  
          
        //����������ɫ���ݻ���  
        //vertices.length*4����Ϊһ��int�������ĸ��ֽ�  
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);  
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��  
        mColorBuffer = cbb.asIntBuffer();//ת��Ϊint�ͻ���  
        mColorBuffer.put(colors);//�򻺳����з��붥����ɫ����  
        mColorBuffer.position(0);//���û�������ʼλ��  
          
          
        int colors1[]=new int[]  
                             {  
                                    0,0,one,0,  
                                    0,0,one,0,            
                                    0,0,one,0,  
                                    one,one,one,0,  
                                    0,0,one,0,  
                                    0,0,one,0,  
                             };  
                             ByteBuffer cbb1 = ByteBuffer.allocateDirect(colors1.length*4);  
                             cbb1.order(ByteOrder.nativeOrder());  
                             mColorBuffer1 = cbb1.asIntBuffer();//ת��Ϊint�ͻ���  
                             mColorBuffer1.put(colors1);//�򻺳����з��붥����ɫ����  
                             mColorBuffer1.position(0);//���û�������ʼλ��  
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer  
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������  
        //������ɫ���ݵĳ�ʼ��================end============================  
          
                           
    }  
  
    public void drawSelf(GL10 gl)  
    {          
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������  
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����  
  
          
        //Ϊ����ָ��������������  
        gl.glVertexPointer  
        (  
                3,              //ÿ���������������Ϊ3  xyz   
                GL10.GL_FLOAT,  //��������ֵ������Ϊ GL_FIXED  
                0,              //����������������֮��ļ��  
                mVertexBuffer   //������������  
        );  
          
          
          
        if(flag){  
            mColor=mColorBuffer;  
        }else{  
            mColor=mColorBuffer1;  
        }  
        //Ϊ����ָ��������ɫ����  
        gl.glColorPointer  
        (  
                4,              //������ɫ����ɳɷ֣�����Ϊ4��RGBA  
                GL10.GL_FIXED,  //������ɫֵ������Ϊ GL_FIXED  
                0,              //����������ɫ����֮��ļ��  
                mColor  //������ɫ����  
        );  
          
          
        //����ͼ��  
        gl.glDrawArrays  
        (  
                GL10.GL_TRIANGLE_FAN,       //�������η�ʽ���  
                0,                      //��ʼ����  
                vCount                  //���������  
        );  
    }  
  
}
