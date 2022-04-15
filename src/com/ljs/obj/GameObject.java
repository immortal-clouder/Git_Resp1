package com.ljs.obj;

import com.ljs.plane.GameWin;

import java.awt.*;
//游戏物体父类；游戏中的我方飞机和敌方飞机，还有敌我发射的子弹都是游戏中的物体，有各自的坐标，长宽和样子
public class GameObject {
    //首先定义物体的图片
    Image img;
    //定义物体的坐标
    int x;
    int y;
    //定义物体的宽高
    int width;
    int height;
    //定义物体的移动速度
    double speed;
    //窗口的引用
    GameWin frame;

    //get、set方法
    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public GameWin getFrame() {
        return frame;
    }

    public void setFrame(GameWin frame) {
        this.frame = frame;
    }

    //有参构造和无参构造,有参构造可以多写几个，应用起来会更灵活

    public GameObject() {

    }

    public GameObject(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }

    public GameObject(Image img, int x, int y, double speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //然后是绘制自身的方法
    public void paintSelf(Graphics gImage){
        gImage.drawImage(img,x,y,null);
    }

    //获取自身矩形的方法
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }
}
