package com.jimmy3d.shape;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
//http://paulbourke.net/miscellaneous/sphere_cylinder/
public class Ball {

	private IntBuffer   mVertexBuffer;//�����������ݻ���
	private IntBuffer   mNormalBuffer;//���㷨�������ݻ���
    private FloatBuffer mTextureBuffer;//�����������ݻ���
    public float mAngleX;//��x����ת�Ƕ�
    public float mAngleY;//��y����ת�Ƕ� 
    public float mAngleZ;//��z����ת�Ƕ� 
    int vCount=0;//��������
    int textureId;//����ID
    public static final int UNIT_SIZE=2000;//10000
    
    public Ball(int scale,int textureId)
    {
    	this.textureId=textureId; 	
        //ʵ�ʶ����������ݵĳ�ʼ��================begin============================
    	
    	ArrayList<Integer> alVertix=new ArrayList<Integer>();//��Ŷ��������ArrayList
    	final int angleSpan=18;//������е�λ�зֵĽǶ�
        for(int vAngle=-90;vAngle<=90;vAngle=vAngle+angleSpan)//��ֱ����angleSpan��һ��
        {
        	for(int hAngle=0;hAngle<360;hAngle=hAngle+angleSpan)//ˮƽ����angleSpan��һ��
        	{//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ�����
        		double xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		int x=(int)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		int z=(int)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		int y=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		//�����������XYZ��������Ŷ��������ArrayList
        		alVertix.add(x);alVertix.add(y);alVertix.add(z);
        		if(vAngle ==0 && hAngle==180){
        			System.out.println("Ball center pos x="+x + "\t y="+y+"\t z="+z);
        		} else if(vAngle==-90 && hAngle == 180){
        			System.out.println("Ball 1 pos x="+x + "\t y="+y+"\t z="+z);
        		}else if(vAngle==90 && hAngle == 180){
        			System.out.println("Ball 2 pos x="+x + "\t y="+y+"\t z="+z);
        		}
        	}
        } 	
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
    	
        //��alVertix�е�����ֵת�浽һ��int������
        int vertices[]=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        //ʵ�ʶ����������ݵĳ�ʼ��================end============================
        
               
        //�����ι��춥�㡢�������������ݳ�ʼ��==========begin==========================
    	alVertix.clear();
        ArrayList<Float> alTexture=new ArrayList<Float>();//����  
        
        int row=(180/angleSpan)+1;//�����зֵ�����
        int col=360/angleSpan;//�����зֵ�����
        for(int i=0;i<row;i++)//��ÿһ��ѭ��
        {
        	if(i>0&&i<row-1)
        	{//�м���
        		for(int j=-1;j<col;j++)
				{//�м��е��������ڵ�����һ�еĶ�Ӧ�㹹��������
					int k=i*col+j;
					//��1�������ζ���					
					alVertix.add(vertices[(k+col)*3]);
					alVertix.add(vertices[(k+col)*3+1]);
					alVertix.add(vertices[(k+col)*3+2]);					
					alTexture.add(0.0f);alTexture.add(0.0f);
					
					//��2�������ζ���		
					alVertix.add(vertices[(k+1)*3]);
					alVertix.add(vertices[(k+1)*3+1]);
					alVertix.add(vertices[(k+1)*3+2]);					
					alTexture.add(1.0f);alTexture.add(1.0f);
					
					//��3�������ζ���
					alVertix.add(vertices[k*3]);
					alVertix.add(vertices[k*3+1]);
					alVertix.add(vertices[k*3+2]);	
					alTexture.add(1.0f);alTexture.add(0.0f);
				}
        		for(int j=0;j<col+1;j++)
				{//�м��е��������ڵ�����һ�еĶ�Ӧ�㹹��������				
					int k=i*col+j;
					
					//��1�������ζ���					
					alVertix.add(vertices[(k-col)*3]);
					alVertix.add(vertices[(k-col)*3+1]);
					alVertix.add(vertices[(k-col)*3+2]);					
					alTexture.add(1f);alTexture.add(1f);
					
					//��2�������ζ���					
					alVertix.add(vertices[(k-1)*3]);
					alVertix.add(vertices[(k-1)*3+1]);
					alVertix.add(vertices[(k-1)*3+2]);					
					alTexture.add(0.0f);alTexture.add(0.0f);
					
					//��3�������ζ���					
					alVertix.add(vertices[k*3]);
					alVertix.add(vertices[k*3+1]);
					alVertix.add(vertices[k*3+2]);					
					alTexture.add(0f);alTexture.add(1f);    
				}
        	}
        }
        
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
    	
        //��alVertix�е�����ֵת�浽һ��int������
        vertices=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        
        //�������ƶ������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��     
        
        //�������㷨�������ݻ���
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mNormalBuffer.put(vertices);//�򻺳����з��붥����������
        mNormalBuffer.position(0);//���û�������ʼλ��
        
        //�����������껺��
        float textureCoors[]=new float[alTexture.size()];//��������ֵ����
        for(int i=0;i<alTexture.size();i++) 
        {
        	textureCoors[i]=alTexture.get(i);
        }
        
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer = cbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mTextureBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
        
        //�����ι��춥�㡢�������������ݳ�ʼ��==========end==============================
    }
    public void setInitF(GL10 gl,float x,float y, float z){
         System.out.println("offsetX="+x+"\t offsetY="+y+"\t offsetZ="+z);
    	gl.glTranslatef(x, y, z);
    }
  
    public void drawSelf(GL10 gl10)
    {   GL11 gl = (GL11)gl10;
    	gl.glRotatef(mAngleZ, 0, 0, 1);//��Z����ת
    	gl.glRotatef(mAngleX, 1, 0, 0);//��X����ת
        gl.glRotatef(mAngleY, 0, 1, 0);//��Y����ת
 
        //����ʹ�ö�������
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
        
        //����ʹ�÷���������
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        //Ϊ����ָ�����㷨��������
        gl.glNormalPointer(GL10.GL_FIXED, 0, mNormalBuffer);
		
        //��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //����ʹ������ST���껺��
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0, 			 			//��ʼ����
        		vCount					//��������
        ); 
    }
    //sphere render
    //http://stackoverflow.com/questions/4859448/android-opengl-sphere-texture-rendering-issue
}

