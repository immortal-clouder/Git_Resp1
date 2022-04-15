package com.ljs.obj;

import com.ljs.plane.GameWin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaneObj extends GameObject {
    public PlaneObj() {
        super();
    }

    public PlaneObj(Image img, int x, int y, double speed) {
        super(img, x, y, speed);
    }

    public PlaneObj(Image img, int x, int y, int width, int height,double speed, GameWin frame) {
        super(img, x, y,width,height,speed,frame);
        //为了实现鼠标控制我方飞机移动，在飞机的构造方法中添加【鼠标滚动事件】
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {  //这是鼠标移动的函数
                //在这里我们让鼠标的x坐标-飞机宽度的一半=飞机的x坐标；鼠标的y坐标-飞机长度的一半=飞机的y坐标
                PlaneObj.super.x= e.getX()-11;
                PlaneObj.super.y= e.getY()-16;
            }
        });
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);

        //我方飞机和敌方boss的碰撞检测
        if (this.frame.bosseObj != null && this.getRec().intersects(this.frame.bosseObj.getRec())){
            //如果发生碰撞，则游戏结束，游戏状态为失败
            GameWin.state = 3;
        }
    }

    @Override
    public Image getImg() {
        return super.getImg();
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }


}
