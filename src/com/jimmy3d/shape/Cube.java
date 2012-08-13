package com.jimmy3d.shape;

import javax.microedition.khronos.opengles.GL10;

/**
 * http://blog.csdn.net/dlnuchunge/article/details/6912044
 * @author Jimmy
 *
 */
public class Cube {
	//���ڻ��Ƹ��������ɫ����  
    ColorRect cr=new ColorRect(Constants.SCALE,Constants.SCALE);  
      
    public void drawSelf(GL10 gl)  
    {  
        //�ܻ���˼�룺ͨ����һ����ɫ������ת��λ��������ÿ�����λ��  
        //�����������ÿ����  
    	gl.glPushMatrix();  
          
        //����ǰС��  
        gl.glPushMatrix();  
        gl.glTranslatef(0, 0, Constants.UNIT_SIZE*Constants.SCALE);  
        cr.drawSelf(gl);          
        gl.glPopMatrix();  
          
        //���ƺ�С��  
        gl.glPushMatrix();        
        gl.glTranslatef(0, 0, -Constants.UNIT_SIZE*Constants.SCALE);  
        gl.glRotatef(180, 0, 1, 0);  
        cr.drawSelf(gl);          
        gl.glPopMatrix();  
          
        //�����ϴ���  
        gl.glPushMatrix();            
        gl.glTranslatef(0,Constants.UNIT_SIZE*Constants.SCALE,0);  
        gl.glRotatef(-90, 1, 0, 0);  
        cr.drawSelf(gl);  
        gl.glPopMatrix();  
          
        //�����´���  
        gl.glPushMatrix();            
        gl.glTranslatef(0,-Constants.UNIT_SIZE*Constants.SCALE,0);  
        gl.glRotatef(90, 1, 0, 0);  
        cr.drawSelf(gl);  
        gl.glPopMatrix();  
          
        //���������  
        gl.glPushMatrix();            
        gl.glTranslatef(Constants.UNIT_SIZE*Constants.SCALE,0,0);         
        gl.glRotatef(-90, 1, 0, 0);  
        gl.glRotatef(90, 0, 1, 0);  
        cr.drawSelf(gl);  
        gl.glPopMatrix();  
          
        //�����Ҵ���  
        gl.glPushMatrix();            
        gl.glTranslatef(-Constants.UNIT_SIZE*Constants.SCALE,0,0);        
        gl.glRotatef(90, 1, 0, 0);  
        gl.glRotatef(-90, 0, 1, 0);  
        cr.drawSelf(gl);  
        gl.glPopMatrix();  
          
        gl.glPopMatrix();  
    }  
      
}
