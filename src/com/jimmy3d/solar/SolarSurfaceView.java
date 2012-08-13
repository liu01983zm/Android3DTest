package com.jimmy3d.solar;

import android.opengl.GLSurfaceView;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.jimmy3d.R;
import com.jimmy3d.shape.Ball;
import com.jimmy3d.shape.DrawCircle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.MotionEvent;
//android 3D-����------������� http://blog.csdn.net/dlnuchunge/article/details/6905455
public class SolarSurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
	private boolean smoothFlag=true;//�Ƿ����ƽ����ɫ
	private int lightAngleGreen=30;//�̹�Ƶĵ�ǰ�Ƕ�
	private int lightAngleRed=30;//���Ƶĵ�ǰ�Ƕ�90
	private int lightAngleBlue = 30;
	
	int textureId;//��������ID
	int textureId2;
	int textureId3;
    boolean isStop = false;
    public final static int BALL_RADIUS = 30;
    public final static int BALL_NUM =8;
    private Context mContext;
public SolarSurfaceView(Context context) {
	super(context);
	mContext= context;
	mRenderer = new SceneRenderer();	//����������Ⱦ��
    setRenderer(mRenderer);				//������Ⱦ��		
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
}

 @Override 
 public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Yλ��
            mRenderer.ball.mAngleX += dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            mRenderer.ball.mAngleZ += dx * TOUCH_SCALE_FACTOR;//������z����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

    public void setSmoothFlag(boolean smoothFlag) {
		this.smoothFlag = smoothFlag;
	}

	public boolean isSmoothFlag() {
		return smoothFlag;
	}


private class SceneRenderer implements GLSurfaceView.Renderer 
{   
	Ball ball;
	Ball[] balls = new Ball[BALL_NUM];
	
	
	DrawCircle circle;
	
	public SceneRenderer()
	{
        //����һ���߳��Զ���ת����
        new Thread()
        {
      	  public void run()
      	  {
      		try
            {
          	  Thread.sleep(1000);//��Ϣ1000ms�ٿ�ʼ����
            }
            catch(Exception e)
            {
          	  e.printStackTrace();
            } 
      		  while(true)
      		  {
      			//lightAngleGreen+=5;//ת���̵�
      			//lightAngleRed+=5;//ת�����
      			lightAngleBlue+=5;
      			  if(ball!=null && !isStop){
      				ball.mAngleY += 2;
      			  }
                requestRender();//�ػ滭��
                try
                {
              	  Thread.sleep(50);//��Ϣ10ms���ػ�
                }
                catch(Exception e)
                {
              	  e.printStackTrace();
                }        			  
      		  }
      	  }
        }.start();
	}
	
	
    public void onDrawFrame(GL10 gl) {            
    	if(smoothFlag)
    	{//����ƽ����ɫ
    		gl.glShadeModel(GL10.GL_SMOOTH);
    	}
    	else
    	{//������ƽ����ɫ
    		gl.glShadeModel(GL10.GL_FLAT);
    	}
    	
    	//�趨��ɫ��Դ��λ��
//    	float lxGreen=(float)(7*Math.cos(Math.toRadians(lightAngleGreen)));
//    	float lzGreen=(float)(7*Math.sin(Math.toRadians(lightAngleGreen)));
//    	float[] positionParamsGreen={lxGreen,0,lzGreen,1};//����1��ʾʹ�ö�λ��
//        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
//        
//        //�趨��ɫ��Դ��λ��
//        float lyRed=(float)(7*Math.cos(Math.toRadians(lightAngleRed)));
//    	float lzRed=(float)(7*Math.sin(Math.toRadians(lightAngleRed)));
//        float[] positionParamsRed={0,lyRed,lzRed,1};//����1��ʾʹ�ö�λ��
//        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsRed,0);        	
//
        //�趨blueɫ��Դ��λ��
        float lxBlue=(float)(7*Math.cos(Math.toRadians(lightAngleBlue)));
    	float lyBlue=(float)(7*Math.sin(Math.toRadians(lightAngleBlue)));
        float[] positionParamsBlue={lxBlue,lyBlue,0,1};//����1��ʾʹ�ö�λ��
        gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_POSITION, positionParamsBlue,0); 
    	 float[] positionParamsWhite={1,1,1,1};//����1��ʾʹ�ö�λ��
    	 gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsWhite,0); 
        
    	//�����ɫ����
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	//���õ�ǰ����Ϊģʽ����
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //���õ�ǰ����Ϊ��λ����
        gl.glLoadIdentity();     
        

        GLU.gluLookAt//��̫���ܱ��ε��ӽǡ���С�ӽ�   ����ͷ
        (  
                gl,   
                
                0f,   //����λ�õ�X  
                10f,    //����λ�õ�Y  
                15.0f,   //����λ�õ�Z  
                
                0,  //�����򿴵ĵ�X  
                0f,   //�����򿴵ĵ�Y  
                0,   //�����򿴵ĵ�Z  
                
                0,   
                1,   
                0  
        );    
        
        
        gl.glTranslatef(0, 0f, -1.8f);  
        //-----------------------BALL ---------------------------------------
        gl.glPushMatrix();//�����任�����ֳ� 
        ball.drawSelf(gl);//������      
        gl.glPopMatrix();//�ָ��任�����ֳ�
        
        gl.glRotatef(ball.mAngleZ, 0, 1, 0);  
        for(int i=0;i<BALL_NUM;i++){
        	 gl.glPushMatrix();//�����任�����ֳ�
             gl.glTranslatef((float)(10f*Math.sin(Math.toRadians(45*i))), 0f, (float)(10f*Math.cos(Math.toRadians(45*i))));
            // ball.drawSelf(gl);//������    
             balls[i].drawSelf(gl);//������     
             gl.glPopMatrix();//�ָ��任�����ֳ� 
        }

        gl.glPushMatrix();//�����任�����ֳ�
        circle.drawSelf(gl);
        gl.glPopMatrix();//�ָ��任�����ֳ�
        //--------------------------------------------------------------
        gl.glPushMatrix();//�����任�����ֳ�
        gl.glRotatef(30f, 0, 0, 1f); //-----------------------------------
        gl.glPopMatrix();//�ָ��任�����ֳ�
    }
    //gluPerspective gluLookAt   http://hi.baidu.com/sunguangran/item/4f189f14bbc794731109b54c
    //͸�Ӻ���glFrustum(), gluPerspective()�����÷� http://hi.baidu.com/zhujianzhai/item/56ded397cf878237336eebeb
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //�����Ӵ���С��λ�� 
//    	gl.glViewport(0, 0, width, height);
//    	//������Χ
//    	GLU.gluPerspective(gl, 
//    	45.0f, // 45 fovy,����������,�ҵ������,�۾������ĽǶ�,��,�ӽǵĴ�С,�������Ϊ0,�൱������۾���,����ʲôҲ������,���Ϊ180,��ô������Ϊ����ӽ�ܹ���, ��������\ /
//    	1.0f * width / height,  //aspect,��������,����ʵ�ʴ��ڵ��ݺ��,��x/y
//    	10.0f,  //zNear,�����,��ʾ�����,�Ĳ���,
//    	1000.0f //zFar��ʾԶ���Ĳ���,
//    	); 
    	
    	
    	
    	
    	
    	//���õ�ǰ����ΪͶӰ����
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //���õ�ǰ����Ϊ��λ����
        gl.glLoadIdentity();
        //����͸��ͶӰ�ı���
        float ratio = (float) width / height;
        //���ô˷����������͸��ͶӰ����
        //gl.glFrustumf(-ratio, ratio, -1, 1,   1, 10); //(1)//<1>if you use gluLookAt ,no anything in viewport<2>not use gluLookAt ,see ball && ball3
       gl.glFrustumf(-ratio, ratio, -1, 1,   1, 1000);//(2)
        //<1>use gluLookAt,see all,but small ,viewport is not same (3<1>) (looks down);
        //<2>not use gluLookAt,see ball && ball3
        
        //void glFrustum(GLdouble left, GLdouble Right, GLdouble bottom, GLdouble top, GLdouble near, GLdouble far);
       //gl.glFrustumf( -1, 1,-ratio, ratio, 1, 100);//(3)
       //���ܱ��ε��ӽǡ������ӽ�<1>use gluLookAt,see ball&& ball2&&ball3 &&circle , but ball is small,  look 3 point in circle table(Horizontal lines) 
       //<2> not use gluLookAt �ܿ���ball ��ball3 ͬ(1<2>)
       
       //gl.glFrustumf( -1, 1,-ratio, ratio, 8f, 100);    //(4) //��̫���ܱ��ε��ӽǡ���С�ӽ�  
       //<1>use gluLookAt ,see 3 ball, but not see circle <2>not use gluLookAt ,only see circle ,like a line
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //�رտ����� 
    	gl.glDisable(GL10.GL_DITHER);
    	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        //������Ļ����ɫ��ɫRGBA
        gl.glClearColor(0.2f,0.4f,0.2f,1.0f);
        //������ɫģ��Ϊƽ����ɫ   
        gl.glShadeModel(GL10.GL_SMOOTH);//GL10.GL_SMOOTH  GL10.GL_FLAT
        //������Ȳ���
        gl.glEnable(GL10.GL_DEPTH_TEST);
        
        gl.glEnable(GL10.GL_LIGHTING);//�������    
        
       
        initGreenLight(gl);//��ʼ����ɫ��
        initRedLight(gl);//��ʼ����ɫ��
        initMaterial(gl);//��ʼ������
        
        textureId=initTexture(gl,R.drawable.bool1);//��ʼ������
        //textureId = loadGLTexture(gl,mContext,R.drawable.album1);
        ball=new Ball(BALL_RADIUS,textureId);
        for(int i =0; i<BALL_NUM;i++){
        	balls[i] = new Ball(BALL_RADIUS,textureId);
        }

        circle  = new DrawCircle(10f,10f,1f,5);//����Բ
        
       
    }
}

//��ʼ����ɫ��
private void initGreenLight(GL10 gl)
{
    gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ�  
    
    //����������
    float[] ambientParams={0.1f,0.1f,0.1f,1.0f};//����� RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
    
    //ɢ�������
    float[] diffuseParams={0f,1f,0f,1.0f};//����� RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
    
    //���������
    float[] specularParams={1f,1f,1f,1.0f};//����� RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);     
}



//��ʼ����ɫ��
private void initRedLight(GL10 gl)
{    
    gl.glEnable(GL10.GL_LIGHT1);//��1�ŵ�  
    
    //����������
    float[] ambientParams={0.2f,0.2f,0.2f,1.0f};//����� RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientParams,0);            
    
    //ɢ�������
    float[] diffuseParams={1f,0f,0f,1.0f};//����� RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams,0); 
    
    //���������
    float[] specularParams={1f,1f,1f,1.0f};//����� RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams,0); 
}

//��ʼ������
private void initMaterial(GL10 gl)
{//����Ϊ��ɫʱʲô��ɫ�Ĺ���������ͽ����ֳ�ʲô��ɫ
    //������Ϊ��ɫ����
    float ambientMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
    //ɢ���Ϊ��ɫ����
    float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
    //�߹����Ϊ��ɫ
    float specularMaterial[] = {1f, 1f, 1f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
    gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
}
//http://stackoverflow.com/questions/4538179/android-opengl-es-1-1-sphere-texture-mapping-problem?rq=1
public int initTexture(GL10 gl,int textureId)//textureId
{
	int[] textures = new int[1];
	gl.glGenTextures(1, textures, 0);    
	int currTextureId=textures[0];    
	gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
    
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
    
    gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
    gl.glEnable(GL10.GL_TEXTURE_2D);
    
    InputStream is = this.getResources().openRawResource(textureId);
    Bitmap bitmapTmp; 
    try 
    {
    	bitmapTmp = BitmapFactory.decodeStream(is);
    } 
    finally 
    {
        try 
        {
            is.close();
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }
    }
    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
    bitmapTmp.recycle(); 
    
    return currTextureId;
}
public int loadGLTexture(GL10 gl10, Context context,int textureId) {
	  GL11 gl = (GL11) gl10;
	  int[] textures_ids = new int[1];
	  gl.glGenTextures(1, textures_ids,0);
	  int currTextureId=textures_ids[0]; 
	  gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
	  gl.glTexParameteri(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
	  gl.glTexParameteri(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
	  //http://www.eoeandroid.com/thread-66130-1-1.html
	  //http://msdn.microsoft.com/zh-cn/library/windows/desktop/dd368639(v=vs.85).aspx
	  //public abstract void glTexParameterf(int target, int pname, float param)
	  //glTexParameterf ������������ͼ�Ĳ�������
	  //����target����ʾ��ʹ�õ�1d������2d��������һά�ģ����Ƕ�ά�ģ���pc�ϣ�����3d������������ͼ��������ͼ�ȣ��ֻ��Ϲ���ֻ��1d��2d��
	  //pname���û��Ʒ�ʽ��
	  //param������˷�ʽ�������Թ��˺�˫���Բ�ֵ�ȡ���
	  gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE );
	  gl.glTexParameterf( GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE );
	  InputStream is = this.getResources().openRawResource(textureId);
	    Bitmap bitmapTmp; 
	    try 
	    {
	    	bitmapTmp = BitmapFactory.decodeStream(is);
	    } 
	    finally 
	    {
	        try 
	        {
	            is.close();
	        } 
	        catch(IOException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
	    bitmapTmp.recycle(); 

//	  gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//	  gl.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
//	  gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//	  gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0,textureBuffer); 
	    return currTextureId;
	 }
}
