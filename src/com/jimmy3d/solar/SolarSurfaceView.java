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
//android 3D-纹理------球的纹理 http://blog.csdn.net/dlnuchunge/article/details/6905455
public class SolarSurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置Y坐标
	private boolean smoothFlag=true;//是否进行平滑着色
	private int lightAngleGreen=30;//绿光灯的当前角度
	private int lightAngleRed=30;//红光灯的当前角度90
	private int lightAngleBlue = 30;
	
	int textureId;//纹理名称ID
	int textureId2;
	int textureId3;
    boolean isStop = false;
    public final static int BALL_RADIUS = 30;
    public final static int BALL_NUM =8;
    private Context mContext;
public SolarSurfaceView(Context context) {
	super(context);
	mContext= context;
	mRenderer = new SceneRenderer();	//创建场景渲染器
    setRenderer(mRenderer);				//设置渲染器		
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
}

 @Override 
 public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔Y位移
            mRenderer.ball.mAngleX += dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            mRenderer.ball.mAngleZ += dx * TOUCH_SCALE_FACTOR;//设置沿z轴旋转角度
            requestRender();//重绘画面
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
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
        //开启一个线程自动旋转球体
        new Thread()
        {
      	  public void run()
      	  {
      		try
            {
          	  Thread.sleep(1000);//休息1000ms再开始绘制
            }
            catch(Exception e)
            {
          	  e.printStackTrace();
            } 
      		  while(true)
      		  {
      			//lightAngleGreen+=5;//转动绿灯
      			//lightAngleRed+=5;//转动红灯
      			lightAngleBlue+=5;
      			  if(ball!=null && !isStop){
      				ball.mAngleY += 2;
      			  }
                requestRender();//重绘画面
                try
                {
              	  Thread.sleep(50);//休息10ms再重绘
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
    	{//进行平滑着色
    		gl.glShadeModel(GL10.GL_SMOOTH);
    	}
    	else
    	{//不进行平滑着色
    		gl.glShadeModel(GL10.GL_FLAT);
    	}
    	
    	//设定绿色光源的位置
//    	float lxGreen=(float)(7*Math.cos(Math.toRadians(lightAngleGreen)));
//    	float lzGreen=(float)(7*Math.sin(Math.toRadians(lightAngleGreen)));
//    	float[] positionParamsGreen={lxGreen,0,lzGreen,1};//最后的1表示使用定位光
//        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
//        
//        //设定红色光源的位置
//        float lyRed=(float)(7*Math.cos(Math.toRadians(lightAngleRed)));
//    	float lzRed=(float)(7*Math.sin(Math.toRadians(lightAngleRed)));
//        float[] positionParamsRed={0,lyRed,lzRed,1};//最后的1表示使用定位光
//        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsRed,0);        	
//
        //设定blue色光源的位置
        float lxBlue=(float)(7*Math.cos(Math.toRadians(lightAngleBlue)));
    	float lyBlue=(float)(7*Math.sin(Math.toRadians(lightAngleBlue)));
        float[] positionParamsBlue={lxBlue,lyBlue,0,1};//最后的1表示使用定位光
        gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_POSITION, positionParamsBlue,0); 
    	 float[] positionParamsWhite={1,1,1,1};//最后的1表示使用定位光
    	 gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsWhite,0); 
        
    	//清除颜色缓存
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	//设置当前矩阵为模式矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //设置当前矩阵为单位矩阵
        gl.glLoadIdentity();     
        

        GLU.gluLookAt//不太可能变形的视角——小视角   摄像头
        (  
                gl,   
                
                0f,   //人眼位置的X  
                10f,    //人眼位置的Y  
                15.0f,   //人眼位置的Z  
                
                0,  //人眼球看的点X  
                0f,   //人眼球看的点Y  
                0,   //人眼球看的点Z  
                
                0,   
                1,   
                0  
        );    
        
        
        gl.glTranslatef(0, 0f, -1.8f);  
        //-----------------------BALL ---------------------------------------
        gl.glPushMatrix();//保护变换矩阵现场 
        ball.drawSelf(gl);//绘制球      
        gl.glPopMatrix();//恢复变换矩阵现场
        
        gl.glRotatef(ball.mAngleZ, 0, 1, 0);  
        for(int i=0;i<BALL_NUM;i++){
        	 gl.glPushMatrix();//保护变换矩阵现场
             gl.glTranslatef((float)(10f*Math.sin(Math.toRadians(45*i))), 0f, (float)(10f*Math.cos(Math.toRadians(45*i))));
            // ball.drawSelf(gl);//绘制球    
             balls[i].drawSelf(gl);//绘制球     
             gl.glPopMatrix();//恢复变换矩阵现场 
        }

        gl.glPushMatrix();//保护变换矩阵现场
        circle.drawSelf(gl);
        gl.glPopMatrix();//恢复变换矩阵现场
        //--------------------------------------------------------------
        gl.glPushMatrix();//保护变换矩阵现场
        gl.glRotatef(30f, 0, 0, 1f); //-----------------------------------
        gl.glPopMatrix();//恢复变换矩阵现场
    }
    //gluPerspective gluLookAt   http://hi.baidu.com/sunguangran/item/4f189f14bbc794731109b54c
    //透视函数glFrustum(), gluPerspective()函数用法 http://hi.baidu.com/zhujianzhai/item/56ded397cf878237336eebeb
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视窗大小及位置 
//    	gl.glViewport(0, 0, width, height);
//    	//视区范围
//    	GLU.gluPerspective(gl, 
//    	45.0f, // 45 fovy,这个最难理解,我的理解是,眼睛睁开的角度,即,视角的大小,如果设置为0,相当你闭上眼睛了,所以什么也看不到,如果为180,那么可以认为你的视界很广阔, 类似这样\ /
//    	1.0f * width / height,  //aspect,这个好理解,就是实际窗口的纵横比,即x/y
//    	10.0f,  //zNear,这个呢,表示你近处,的裁面,
//    	1000.0f //zFar表示远处的裁面,
//    	); 
    	
    	
    	
    	
    	
    	//设置当前矩阵为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //设置当前矩阵为单位矩阵
        gl.glLoadIdentity();
        //计算透视投影的比例
        float ratio = (float) width / height;
        //调用此方法计算产生透视投影矩阵
        //gl.glFrustumf(-ratio, ratio, -1, 1,   1, 10); //(1)//<1>if you use gluLookAt ,no anything in viewport<2>not use gluLookAt ,see ball && ball3
       gl.glFrustumf(-ratio, ratio, -1, 1,   1, 1000);//(2)
        //<1>use gluLookAt,see all,but small ,viewport is not same (3<1>) (looks down);
        //<2>not use gluLookAt,see ball && ball3
        
        //void glFrustum(GLdouble left, GLdouble Right, GLdouble bottom, GLdouble top, GLdouble near, GLdouble far);
       //gl.glFrustumf( -1, 1,-ratio, ratio, 1, 100);//(3)
       //可能变形的视角——大视角<1>use gluLookAt,see ball&& ball2&&ball3 &&circle , but ball is small,  look 3 point in circle table(Horizontal lines) 
       //<2> not use gluLookAt 能看到ball 和ball3 同(1<2>)
       
       //gl.glFrustumf( -1, 1,-ratio, ratio, 8f, 100);    //(4) //不太可能变形的视角——小视角  
       //<1>use gluLookAt ,see 3 ball, but not see circle <2>not use gluLookAt ,only see circle ,like a line
    }

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
        
       
        initGreenLight(gl);//初始化绿色灯
        initRedLight(gl);//初始化红色灯
        initMaterial(gl);//初始化材质
        
        textureId=initTexture(gl,R.drawable.bool1);//初始化纹理
        //textureId = loadGLTexture(gl,mContext,R.drawable.album1);
        ball=new Ball(BALL_RADIUS,textureId);
        for(int i =0; i<BALL_NUM;i++){
        	balls[i] = new Ball(BALL_RADIUS,textureId);
        }

        circle  = new DrawCircle(10f,10f,1f,5);//创建圆
        
       
    }
}

//初始化绿色灯
private void initGreenLight(GL10 gl)
{
    gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
    
    //环境光设置
    float[] ambientParams={0.1f,0.1f,0.1f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
    
    //散射光设置
    float[] diffuseParams={0f,1f,0f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
    
    //反射光设置
    float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);     
}



//初始化红色灯
private void initRedLight(GL10 gl)
{    
    gl.glEnable(GL10.GL_LIGHT1);//打开1号灯  
    
    //环境光设置
    float[] ambientParams={0.2f,0.2f,0.2f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientParams,0);            
    
    //散射光设置
    float[] diffuseParams={1f,0f,0f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams,0); 
    
    //反射光设置
    float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams,0); 
}

//初始化材质
private void initMaterial(GL10 gl)
{//材质为白色时什么颜色的光照在上面就将体现出什么颜色
    //环境光为白色材质
    float ambientMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
    //散射光为白色材质
    float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
    //高光材质为白色
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
	  //glTexParameterf 是设置纹理贴图的参数属性
	  //比如target，表示你使用的1d纹理还是2d纹理，就是一维的，还是二维的，在pc上，还有3d纹理，立方体贴图和球面贴图等，手机上估计只有1d和2d；
	  //pname设置环绕方式；
	  //param纹理过滤方式，如线性过滤和双线性插值等。。
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
