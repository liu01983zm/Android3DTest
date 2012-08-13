package cover;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;
/**
 * http://www.cnblogs.com/jacktu/MyPosts.html
 * http://www.ophonesdn.com/article/show/138
 * (ophonecsdn 3d article)code: http://download.csdn.net/detail/xueyong1203/3414169
 * ogles opengl android: http://code.google.com/p/ogles-tutorial-android/
 * */
public class CoverGLSurfaceView extends GLSurfaceView {
	CoverRenderer mRenderer;
	private float mPreviousY;//�ϴεĴ���λ��Y����
	private float mPreviousX;//�ϴεĴ���λ��Y����
	final float MIN_DIS = 5;
	public CoverGLSurfaceView(Context context) {
		super(context);
		mRenderer = new CoverRenderer();
		setRenderer(mRenderer);
		//setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
	}
	/** * ��Ӧ�����¼� */
	@Override 
	public boolean onTouchEvent(MotionEvent e) { 
		float x = e.getX();
		switch (e.getAction()) { 
		case MotionEvent.ACTION_MOVE: 
			float dx = x - mPreviousX; 
			if(dx > MIN_DIS) { 
				queueEvent(new Runnable() {
				// This method will be called on the rendering // thread: 
					public void run() { 
						//mRenderer.slideLeft(); 
						}}); 
				} else if(dx < -MIN_DIS) {
					queueEvent(new Runnable() { 
						// This method will be called on the rendering // thread: 
						public void run() { 
							//mRenderer.slideRight(); 
							}
						}
					); 
				} else { 
						//do nothing 
						
				} 
		}
		mPreviousX = x; 
		return true; 
		} 
	
	
	/** * ��Ӧ�����¼� */ 
	@Override 	
	public boolean onKeyDown(int keyCode,KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) { queueEvent(new Runnable() { 
			// This method will be called on the rendering // thread: 
			public void run() { 
				//mRenderer.slideLeft(); 
				}}); 
		    return true; 
		} else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) { 
			queueEvent(new Runnable() { 
				// This method will be called on the rendering // thread: 
				public void run() { 
					//mRenderer.slideRight(); 
				}}); 
			return true; 
			} 
		return super.onKeyDown(keyCode, event); 
	}  
			
	

   

	 class CoverRenderer implements Renderer{
		    private float _red = 0.2f;
		    private float _green = 0.9f;
		    private float _blue = 0.2f;
			@Override
			public void onDrawFrame(GL10 gl) {
				// TODO Auto-generated method stub
				
			}

			@Override
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
		        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 20);
			}

			@Override
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
		        //��ʼ������
		        //��ʼ������
		        //��ʼ������
		        //��ʼ��Mesh
			}
			 public void setColor(float r, float g, float b) {
			        _red = r;
			        _green = g;
			        _blue = b;
			    }
			 public void createCover() { //������16�����㣬���ﻹ���������� �ĵ�Ӱ������һ��Ҫ����32�� 
				 Vertex[] pVertices = new Vertex[16 * 2]; 
				 // ��ʼ������ 
				 for (int i = 0; i < pVertices.length; i++) { pVertices[i] = new Vertex(); } 
				 // ���÷����ʼ����������ȸ߶Ⱦ�Ϊ6.0f, ������뵹Ӱ�ľ�����0 
				 // �����Ҫ��ʾ������ͼƬ�������������޸�ͼƬ���� 
				 float width = 6.0f, height = 6.0f, heightFromMirror = 0.0f; float dim = 0.5f; // as uv 
				 float mfBorderFraction = 0.05f;//���ɱ�Ե���ϵ�����������Ϊ0�ͱ�ʾû�б�Ե���� 
				 float dimLess = 0.5f - (0.5f * mfBorderFraction); //���ö���ķ��ߣ���������û�����ù��գ���˲�δ�ر����� 
				 Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f); //���ö����λ�� 
				 pVertices[0].p.set(-dim, dim, 0); 
				 pVertices[1].p.set(-dimLess, dim, 0); 
				 pVertices[2].p.set(dimLess, dim, 0); 
				 pVertices[3].p.set(dim, dim, 0); 
				 pVertices[4].p.set(-dim, dimLess, 0); 
				 pVertices[5].p.set(-dimLess, dimLess, 0); 
				 pVertices[6].p.set(dimLess, dimLess, 0); 
				 pVertices[7].p.set(dim, -dimLess, 0); 
				 pVertices[8].p.set(-dim, -dimLess, 0); 
				 pVertices[9].p.set(-dimLess, -dimLess, 0); 
				 pVertices[10].p.set(dimLess, -dimLess, 0); 
				 pVertices[11].p.set(dim, -dimLess, 0); 
				 pVertices[12].p.set(-dim, -dim, 0); 
				 pVertices[13].p.set(-dimLess, -dim, 0); 
				 pVertices[14].p.set(dimLess, -dim, 0); 
				 pVertices[15].p.set(dim, -dim, 0); 
				 //�������ж������ɫ�ͷ��� 
				 for (int i = 0; i < 16; i++) { 
					 //���÷��� 
					 pVertices[i].n.set(normal); 
					 //���ö���ɫ 
					 pVertices[i].c.set(1.0f, 1.0f, 1.0f, 0.0f); 
					 //������������ 
					 pVertices[i].t.x = pVertices[i].p.x + 0.5f; pVertices[i].t.y = pVertices[i].p.y + 0.5f; 
					 //���ö���λ�� 
					 pVertices[i].p.x = pVertices[i].p.x * width; pVertices[i].p.y = pVertices[i].p.y * height; 
					 } 
				 //�������������4�����㣬����Ϊ��ȫ��͸������alpha = 1.0 
				 pVertices[5].c.w = 1.0f; 
				 pVertices[6].c.w = 1.0f; 
				 pVertices[9].c.w = 1.0f; 
				 pVertices[10].c.w = 1.0f; 
				 //��������Ӱ 
				 for (int row = 0; row < 4; ++row) { 
					 //���ݾ���ĸ߶ȵ�������ɫ���� 
					 float dark = 1 - ((pVertices[row * 4].p.y / height) + 0.5f); dark -= 0.5f; 
					 for (int col = 0; col < 4; col++) { 
						 int offset = row * 4 + col; 
						 //����Ӱ����Ķ��㴹ֱ��ת
						 pVertices[offset + 16].set(pVertices[offset]); 
						 pVertices[offset + 16].p.y = -pVertices[offset + 16].p.y; 
						 pVertices[offset + 16].p.y -= height + heightFromMirror; 
						 //���õ�Ӱ���漸����Ķ�����ɫ
						 pVertices[offset + 16].c.x = dark; 
						 pVertices[offset + 16].c.y = dark;    
					 }
				 }
			 }
		   }
}
