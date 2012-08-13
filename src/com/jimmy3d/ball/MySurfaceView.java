package com.jimmy3d.ball;

import android.opengl.GLSurfaceView;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jimmy3d.R;
import com.jimmy3d.shape.Ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;
public class MySurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
	private boolean smoothFlag=true;//�Ƿ����ƽ����ɫ
	private int lightAngleGreen=0;//�̹�Ƶĵ�ǰ�Ƕ�
	private int lightAngleRed=90;//���Ƶĵ�ǰ�Ƕ�
	
	int textureId;//��������ID

public MySurfaceView(Context context) {
	super(context);
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
      			lightAngleGreen+=5;//ת���̵�
      			lightAngleRed+=5;//ת�����
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
    	float lxGreen=(float)(7*Math.cos(Math.toRadians(lightAngleGreen)));
    	float lzGreen=(float)(7*Math.sin(Math.toRadians(lightAngleGreen)));
    	float[] positionParamsGreen={lxGreen,0,lzGreen,1};//����1��ʾʹ�ö�λ��
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
        
        //�趨��ɫ��Դ��λ��
        float lyRed=(float)(7*Math.cos(Math.toRadians(lightAngleRed)));
    	float lzRed=(float)(7*Math.sin(Math.toRadians(lightAngleRed)));
        float[] positionParamsRed={0,lyRed,lzRed,1};//����1��ʾʹ�ö�λ��
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsRed,0);        	
    	
    	//�����ɫ����
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	//���õ�ǰ����Ϊģʽ����
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //���õ�ǰ����Ϊ��λ����
        gl.glLoadIdentity();     
        
        gl.glTranslatef(0, 0f, -1.8f);  
        
        gl.glPushMatrix();//�����任�����ֳ�
        ball.drawSelf(gl);//������
        gl.glPopMatrix();//�ָ��任�����ֳ�
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //�����Ӵ���С��λ�� 
    	gl.glViewport(0, 0, width, height);
    	//���õ�ǰ����ΪͶӰ����
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //���õ�ǰ����Ϊ��λ����
        gl.glLoadIdentity();
        //����͸��ͶӰ�ı���
        float ratio = (float) width / height;
        //���ô˷����������͸��ͶӰ����
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //�رտ����� 
    	gl.glDisable(GL10.GL_DITHER);
    	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        //������Ļ����ɫ��ɫRGBA
        gl.glClearColor(0,0,0,0);
        //������ɫģ��Ϊƽ����ɫ   
        gl.glShadeModel(GL10.GL_SMOOTH);//GL10.GL_SMOOTH  GL10.GL_FLAT
        //������Ȳ���
        gl.glEnable(GL10.GL_DEPTH_TEST);
        
        gl.glEnable(GL10.GL_LIGHTING);//�������    
        
        initGreenLight(gl);//��ʼ����ɫ��
        initRedLight(gl);//��ʼ����ɫ��
        initMaterial(gl);//��ʼ������
        
        textureId=initTexture(gl,R.drawable.duke);//��ʼ������
        ball=new Ball(4,textureId);
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
}
