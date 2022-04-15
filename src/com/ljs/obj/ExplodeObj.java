package com.ljs.obj;

import java.awt.*;

public class ExplodeObj extends GameObject {
    //爆炸动画实际是由一系列静态图快速切换而产生的视觉效果，所以我们需要定义一个Image 类型的静态数组
    static  Image[] pic = new Image[16];

    //爆炸爆炸动画只显示一次，需要定义一个变量，来限制每次爆炸的动画只显示一次
    int explodeCount = 0;
    //通过一个静态代码块将16张爆炸图片添加到图片数组中
    static{
        for (int i = 0;i < pic.length;i++){
            pic[i]=Toolkit.getDefaultToolkit().getImage("imgs/explode/e"+(i+1)+".gif");
        }
    }

    public ExplodeObj(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintSelf(Graphics gImage) {
        if (explodeCount < 16){
            img = pic[explodeCount];
            super.paintSelf(gImage);
            explodeCount++;
        }
    }
}
