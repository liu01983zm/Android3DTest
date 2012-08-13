package com.jimmy3d.cricle;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.jimmy3d.shape.DrawCircle;
import com.jimmy3d.shape.DrawCylinder;

import android.content.Context;
import android.view.MotionEvent;
public class MyGLSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
 
 public MyGLSurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer(); //����������Ⱦ��
        setRenderer(mRenderer);    //������Ⱦ��  
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }   
   
    //�����¼��ص����� 
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Yλ��
            mRenderer.cylinder.mAngleX += dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            mRenderer.cylinder.mAngleZ += dx * TOUCH_SCALE_FACTOR;//������z����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }
 private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
     DrawCylinder cylinder;//����Բ����
     
        public void onDrawFrame(GL10 gl) {                 
         //�����ɫ����
         gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
         //���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();     
                  
            gl.glPushMatrix();//�����任�����ֳ�     
            gl.glTranslatef(0, 0, -8f);//ƽ��
            cylinder.drawSelf(gl);//���� 
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
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100);
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //�رտ����� 
         gl.glDisable(GL10.GL_DITHER);
         //�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //������Ļ����ɫ��ɫRGBA
            gl.glClearColor(1,1,1,1);
            //������ɫģ��Ϊƽ����ɫ   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //������Ȳ���
            gl.glEnable(GL10.GL_DEPTH_TEST);
          
            cylinder=new DrawCylinder(10f,2f,1f,5);//����Բ����
           
        }
    }
}