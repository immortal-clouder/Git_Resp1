package com.ljs.obj;

import com.ljs.plane.GameWin;
import com.ljs.utils.GameUtils;

import java.awt.*;

public class BossObj extends GameObject {

    //定义一个生命值变量
    int life =100;

    public BossObj() {
        super();
    }

    public BossObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        //实现boss横向移动
        if (x > 550 || x<0){
            speed = -speed;
        }
        x += speed;

        //敌方boss与我方子弹碰撞检测
        for (ShellObj shellObj : GameUtils.shellObjList){
            if (this.getRec().intersects(shellObj.getRec())){
                //如果条件判断为true，则说明二者已经碰撞，子弹会在爆炸中消失，boss生命值-1，执行以下代码
                //首先将二者的坐标改变一下,先让他们消失在游戏界面中
                shellObj.setX(-100);
                shellObj.setY(100);
                //然后将二者添加的removeList集合中
                GameUtils.removeList.add(shellObj);
                life--;
            }
            if (life <= 0 ){
                //敌方boss坠机，游戏通关，改变游戏状态为4
                GameWin.state = 4;
            }
        }

        //boss血条的白色背景
        gImage.setColor(Color.white);
        gImage.fillRect(20,40,100,10);
        //血条的绘制
        gImage.setColor(Color.red);
        gImage.fillRect(20,40,life*100/100,10);
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
