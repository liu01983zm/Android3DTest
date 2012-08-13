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
	private float mPreviousY;//上次的触控位置Y坐标
	private float mPreviousX;//上次的触控位置Y坐标
	final float MIN_DIS = 5;
	public CoverGLSurfaceView(Context context) {
		super(context);
		mRenderer = new CoverRenderer();
		setRenderer(mRenderer);
		//setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
	}
	/** * 响应触屏事件 */
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
	
	
	/** * 响应按键事件 */ 
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
				 //设置视窗大小及位置 
		    	gl.glViewport(0, 0, width, height);
		    	//设置当前矩阵为投影矩阵
		        gl.glMatrixMode(GL10.GL_PROJECTION);
		        //设置当前矩阵为单位矩阵
		        gl.glLoadIdentity();
		        //计算透视投影的比例
		        float ratio = (float) width / height;
		        //调用此方法计算产生透视投影矩阵
		        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 20);
			}

			@Override
			public void onSurfaceCreated(GL10 gl, EGLConfig config) {
				 //关闭抗抖动 
		    	gl.glDisable(GL10.GL_DITHER);
		    	//设置特定Hint项目的模式，这里为设置为使用快速模式
		        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
		        //设置屏幕背景色黑色RGBA
		        gl.glClearColor(0.2f,0.4f,0.2f,1.0f);
		        //设置着色模型为平滑着色   
		        gl.glShadeModel(GL10.GL_SMOOTH);//GL10.GL_SMOOTH  GL10.GL_FLAT
		        //启用深度测试
		        gl.glEnable(GL10.GL_DEPTH_TEST);
		        
		        gl.glEnable(GL10.GL_LIGHTING);//允许光照    
		        //初始化光照
		        //初始化材质
		        //初始化纹理
		        //初始化Mesh
			}
			 public void setColor(float r, float g, float b) {
			        _red = r;
			        _green = g;
			        _blue = b;
			    }
			 public void createCover() { //封面有16个顶点，这里还包括它自身 的倒影，所以一共要创建32个 
				 Vertex[] pVertices = new Vertex[16 * 2]; 
				 // 初始化操作 
				 for (int i = 0; i < pVertices.length; i++) { pVertices[i] = new Vertex(); } 
				 // 设置封面初始化参数，宽度高度均为6.0f, 封面距离倒影的距离是0 
				 // 如果需要显示宽屏的图片，可以在这里修改图片比例 
				 float width = 6.0f, height = 6.0f, heightFromMirror = 0.0f; float dim = 0.5f; // as uv 
				 float mfBorderFraction = 0.05f;//过渡边缘宽度系数，如果设置为0就表示没有边缘过渡 
				 float dimLess = 0.5f - (0.5f * mfBorderFraction); //设置顶点的法线，这里由于没有启用光照，因此并未特别设置 
				 Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f); //设置顶点的位置 
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
				 //设置所有顶点的颜色和法线 
				 for (int i = 0; i < 16; i++) { 
					 //设置法线 
					 pVertices[i].n.set(normal); 
					 //设置顶点色 
					 pVertices[i].c.set(1.0f, 1.0f, 1.0f, 0.0f); 
					 //设置纹理坐标 
					 pVertices[i].t.x = pVertices[i].p.x + 0.5f; pVertices[i].t.y = pVertices[i].p.y + 0.5f; 
					 //设置顶点位置 
					 pVertices[i].p.x = pVertices[i].p.x * width; pVertices[i].p.y = pVertices[i].p.y * height; 
					 } 
				 //对于中心区域的4个顶点，设置为完全不透明，即alpha = 1.0 
				 pVertices[5].c.w = 1.0f; 
				 pVertices[6].c.w = 1.0f; 
				 pVertices[9].c.w = 1.0f; 
				 pVertices[10].c.w = 1.0f; 
				 //创建镜像倒影 
				 for (int row = 0; row < 4; ++row) { 
					 //根据镜像的高度调整顶点色明暗 
					 float dark = 1 - ((pVertices[row * 4].p.y / height) + 0.5f); dark -= 0.5f; 
					 for (int col = 0; col < 4; col++) { 
						 int offset = row * 4 + col; 
						 //将倒影封面的顶点垂直翻转
						 pVertices[offset + 16].set(pVertices[offset]); 
						 pVertices[offset + 16].p.y = -pVertices[offset + 16].p.y; 
						 pVertices[offset + 16].p.y -= height + heightFromMirror; 
						 //设置倒影封面几何体的顶点颜色
						 pVertices[offset + 16].c.x = dark; 
						 pVertices[offset + 16].c.y = dark;    
					 }
				 }
			 }
		   }
}
