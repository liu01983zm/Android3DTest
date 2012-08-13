package com.jimmy3d.cube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jimmy3d.shape.ColorRect;
import com.jimmy3d.shape.Cube;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class CubeSurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���  
    private SceneRenderer mRenderer;//������Ⱦ��   
  
    private float mPreviousX;//�ϴεĴ���λ��X����  
      
    public CubeSurfaceView(Context context) {  
        super(context);  
        mRenderer = new SceneRenderer();    //����������Ⱦ��  
        setRenderer(mRenderer);             //������Ⱦ��       
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ     
    }  
      
    //�����¼��ص�����  
    @Override   
    public boolean onTouchEvent(MotionEvent e) {  
        float x = e.getX();  
        switch (e.getAction()) {  
        case MotionEvent.ACTION_MOVE:  
            float dx = x - mPreviousX;//���㴥�ر�Xλ��  
            mRenderer.angle += dx * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�  
            requestRender();  
             
            //�ػ滭��  
        }     
        mPreviousX = x;//��¼���ر�λ��  
        return true;  
    }  
      
    public boolean onKeyDown(int keyCode,KeyEvent event){  
        if(keyCode==KeyEvent.KEYCODE_DPAD_UP)  
        {  
            mRenderer.x +=0.5f;  
        }  
        if(keyCode==KeyEvent.KEYCODE_DPAD_DOWN){  
            mRenderer.x -=0.5f;  
        }  
        if(keyCode==KeyEvent.KEYCODE_DPAD_LEFT){  
             mRenderer.angle+=90;  
        }  
        if(keyCode==KeyEvent.KEYCODE_DPAD_RIGHT){  
             mRenderer.angle+=90;  
        }  
        return super.onKeyDown(keyCode, event);  
    }  
      
  
    private class SceneRenderer implements GLSurfaceView.Renderer   
    {     
        Cube cube=new Cube();//������  
        float angle=45;//����ת�Ƕ�  
        float x=0,y=0,z=0;  
        public void onDrawFrame(GL10 gl) {  
            //����Ϊ�򿪱������  
            gl.glEnable(GL10.GL_CULL_FACE);  
  
            //������ɫģ��Ϊƽ����ɫ     
            gl.glShadeModel(GL10.GL_SMOOTH);  
              
            //�����ɫ��������Ȼ���  
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
              
            //��ת������ϵ  
            gl.glRotatef(angle, 0, 1, 0);  
              
            //������������  
            gl.glPushMatrix();  
            gl.glTranslatef(x, y, z);  
            cube.drawSelf(gl);  
            gl.glPopMatrix();  
              
              
            gl.glPushMatrix();  
            gl.glTranslatef(2, 0, 0);  
            cube.drawSelf(gl);  
            gl.glPopMatrix();  
            if(x>1.0f&&x<3.0f){  
                ColorRect.flag=false;  
            }else{  
                ColorRect.flag=true;  
            }  
  
        }  
          
        public void onSurfaceChanged(GL10 gl, int width, int height) {  
            //�����Ӵ���С��λ��   
            gl.glViewport(0, 0, width, height);  
            //���õ�ǰ����ΪͶӰ����  
            gl.glMatrixMode(GL10.GL_PROJECTION);  
            //���õ�ǰ����Ϊ��λ����  
            gl.glLoadIdentity();  
            //����͸��ͶӰ�ı���  
            float ratio = (float) height/width ;  
            //���ô˷����������͸��ͶӰ����  
            //gl.glFrustumf( -1, 1,-ratio, ratio, 1, 100);   //���ܱ��ε��ӽǡ������ӽ�    
            gl.glFrustumf( -1, 1,-ratio, ratio, 8f, 100);     //��̫���ܱ��ε��ӽǡ���С�ӽ�  
        }  
  
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {  
            //�رտ�����   
            gl.glDisable(GL10.GL_DITHER);  
            //�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ  
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);  
            //������Ļ����ɫ��ɫRGBA  
            gl.glClearColor(0,0,0,0);              
            //������Ȳ���  
            gl.glEnable(GL10.GL_DEPTH_TEST);  
        }  
    }  
}
