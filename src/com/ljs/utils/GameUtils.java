package com.ljs.utils;

import com.ljs.obj.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//创建一个工具类，在工具类中获取桌面窗口的背景图片
public class GameUtils {
    //桌面窗口背景图片
    public static Image bgImg = Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");

    //boss图片
    public static Image bossImg = Toolkit.getDefaultToolkit().getImage("imgs/boss.png");

    //爆炸图片
    public static Image explodeImg = Toolkit.getDefaultToolkit().getImage("imgs/explode/e6.gif");

    //我方战斗机图片
    public static Image planeImg = Toolkit.getDefaultToolkit().getImage("imgs/plane.png");

    //我方战斗机子弹的图片
    public static Image shellImg = Toolkit.getDefaultToolkit().getImage("imgs/shell.png");

    //敌方战斗机的图片
    public static Image enemyImg = Toolkit.getDefaultToolkit().getImage("imgs/enemy.png");

    //敌方子弹的图片
    public static Image bulletImg = Toolkit.getDefaultToolkit().getImage("imgs/bullet.png");

    //为了实现我方战斗机子弹批量添加，首先创建一个我方子弹的集合
    public static List<ShellObj> shellObjList = new ArrayList<>();

    //敌方战斗机的集合
    public static List<EnemyObj> enemyObjList = new ArrayList<>();

    //敌方子弹集合
    public static List<BulletObj> bulletObjList = new ArrayList<>();

    //所有游戏物体的集合
    public static List<GameObject> GameObjList = new ArrayList<>();

    //被删除的物体的集合
    public static List<GameObject> removeList = new ArrayList<>();

    //爆炸的集合
    public static List<ExplodeObj> explodeList = new ArrayList<>();

    //定义绘制字符串的工具类
    public static void drawWord(Graphics gImage,String str,Color color,int size,int x,int y){
        gImage.setColor(color);
        gImage.setFont(new Font("仿宋",Font.BOLD,size));
        gImage.drawString(str,x,y);

    }
}
