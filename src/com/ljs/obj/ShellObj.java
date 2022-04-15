package com.ljs.obj;

import com.ljs.plane.GameWin;
import com.ljs.utils.GameUtils;

import java.awt.*;
import java.awt.event.KeyAdapter;

public class ShellObj extends GameObject{
    public ShellObj(Image shellImg, int x, int y, int width, int height, int speed, KeyAdapter keyAdapter) {
    }

    @Override
    public Image getImg() {
        return super.getImg();
    }

    public ShellObj() {
        super();
    }

    public ShellObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        super.paintSelf(gImage);
        //实现子弹的移动
        y -= speed;

        if (y < 0){ //说明我方子弹已经跑出了窗口
            this.x = -100;
            this.y = 100;
            GameUtils.removeList.add(this);
        }

    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
