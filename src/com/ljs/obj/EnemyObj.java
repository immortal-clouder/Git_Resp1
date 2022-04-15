package com.ljs.obj;

import com.ljs.plane.GameWin;
import com.ljs.utils.GameUtils;

import java.awt.*;

public class EnemyObj extends GameObject{
    public EnemyObj() {
        super();
    }

    public EnemyObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        //敌机是向下走的，所以纵坐标递增
        y += speed;

        //敌我双方飞机碰撞检测
        if (this.getRec().intersects(this.frame.planeObj.getRec())){
            //如果发生碰撞，则游戏结束，游戏状态为失败
            GameWin.state = 3;
        }

        //敌方飞机与我方子弹的碰撞检测
        //思路：游戏中的所有物体都是一个矩形，检测两个物体有没有碰撞，就是检测两个矩形有没有重叠的部分
        //那么实现方方就是让每个敌机与我方子弹集合中的每一个元素进行一一检测
        //for循环遍历我方子弹集合shellList
        for (ShellObj shellObj : GameUtils.shellObjList){
            if (this.getRec().intersects(shellObj.getRec())){
                //如果条件判断为true，则说明二者已经碰撞，两者应该都会在爆炸中消失，执行以下代码
                //首先将二者的坐标改变一下,先让他们消失在游戏界面中
                ExplodeObj explodeObj = new ExplodeObj(this.getX(),this.getY());    //爆炸的位置为当前敌机碰撞的位置
                GameUtils.explodeList.add(explodeObj);
                GameUtils.removeList.add(explodeObj);   //防止集合过长导致集合崩溃，所以还要将爆炸对象添加到removeList中
                shellObj.setX(-100);
                shellObj.setY(100);
                this.x = -200;
                this.y = 200;
                //然后将二者添加的removeList集合中
                GameUtils.removeList.add(shellObj);
                GameUtils.removeList.add(this);
                GameWin.score++;
            }
        }

        if (y > 600){ //说明敌方飞机已经跑出了窗口
            this.x = -200;
            this.y = 200;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() { //这个方法就是获取每个物体所在的矩形
        return super.getRec();
    }
}
