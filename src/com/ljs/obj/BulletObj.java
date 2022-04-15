package com.ljs.obj;

import com.ljs.plane.GameWin;
import com.ljs.utils.GameUtils;

import java.awt.*;

public class BulletObj extends GameObject {
    public BulletObj() {
        super();
    }

    public BulletObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        y += speed;

        //敌方子弹和我方战机碰撞检测
        if (this.getRec().intersects(this.frame.planeObj.getRec())){
            //如果发生碰撞，则游戏结束，游戏状态为失败
            GameWin.state = 3;
        }

        if (y > 600){ //说明敌方子弹已经跑出了窗口
            this.x = -300;
            this.y = 300;
            GameUtils.removeList.add(this);
        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
