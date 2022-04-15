package com.ljs.plane;

import com.ljs.obj.*;
import com.ljs.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//创建简单桌面窗口
//普通类继承JFrame类，便可以创建桌面窗口
public class GameWin extends JFrame {

    //游戏状态:0未开始；1游戏中；2暂停；3通关失败；通关成功

    //定义一个变量，来记录游戏画面的重绘次数
    int count = 1;

    //我方战机每击落一架飞机，我方分数+1，定义变量score记录分数
    public static int score = 0;

    //当出现一百架敌机之后，敌方boss才出现，所以要先定义一个变量记录敌机出现的架数
    public static int enemyCount = 0;

    //双缓存技术代码实现；首先定义一个变量
    Image offScreenImage = null;

    int width = 600;
    int height = 600;

    //创建我方飞机对象
    public PlaneObj planeObj = new PlaneObj(GameUtils.planeImg,290,550,20,30,0,this);

    //创建敌方boss对象
        public BossObj bosseObj = null;

   /* //创建我方战斗机子弹的对象,子弹的位置是和战斗机的位置相关联的，但稍微有点偏移,这种做饭只能添加一发子弹
    ShellObj shellObj = new ShellObj(GameUtils.shellImg,planeObj.getX()+3,planeObj.getY()-16,14,29,5,this);*/

    //先定义游戏的起始状态
    public static int state = 0;

    //在窗口类中获得背景类的对象
    BgObj bgObj = new BgObj(GameUtils.bgImg,0,-2000,2);   //y为什么设置为-2000

    public void launch(){
        //将所有要绘制的游戏物体都添加到gameObjList集合中去
        GameUtils.GameObjList.add(bgObj);
        GameUtils.GameObjList.add(planeObj);
        //GameUtils.GameObjList.add(bosseObj);

        //设置窗口是否可见
        this.setVisible(true);  //默认是false
        //设置窗口大小
         this.setSize(width,height);
         //设置窗口的位置，这里我们将窗口放在屏幕的中央
        this.setLocationRelativeTo(null);
        //设置窗口的标题
        this.setTitle("打飞机");

        //采用空格键实现暂停功能，所以要添加键盘监听事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //空格键的代号为32
                if (e.getKeyCode() == 32){
                    switch (state){
                        case 1:
                            state = 2;
                            break;
                        case 2:
                            state = 1;
                            break;
                        default:
                    }
                }
            }
        });

        //添加鼠标监听事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //在鼠标点击事件中我们添加一个判断条件
                //游戏未开始的情况下点击鼠标左键；e.getButton() == 1表示点击鼠标左键
                if (e.getButton() == 1 && state == 0){
                    //此时点击左键后，游戏需要将游戏状态更改为游戏中
                    state = 1;
                    //使用repaint方法，这样它会重新调用paint方法
                    repaint();
                }else if (e.getButton() == 1 && state == 3){
                    //更改游戏状态state为初始状态，重置分数score，敌机数量，删除物体
                    state = 0;
                    score = 0;
                    enemyCount = 0;
                    GameUtils.removeList.addAll(GameUtils.enemyObjList);
                    GameUtils.removeList.addAll(GameUtils.bulletObjList);
                    GameUtils.removeList.addAll(GameUtils.shellObjList);
                    GameUtils.removeList.add(bosseObj); //删除boss物体
                    bosseObj = null;    //删除boss对象
                    //若重新开始，则在下一次绘制之前将碰撞的物体从GameObjList集合中删除
                    GameUtils.GameObjList.removeAll(GameUtils.removeList);
                    repaint();
                }else if (e.getButton() == 1 && state == 4){
                    //更改游戏状态state为初始状态，重置分数score，敌机数量，删除物体
                    state = 0;
                    score = 0;
                    enemyCount = 0;
                    GameUtils.removeList.addAll(GameUtils.enemyObjList);
                    GameUtils.removeList.addAll(GameUtils.bulletObjList);
                    GameUtils.removeList.addAll(GameUtils.shellObjList);
                    GameUtils.removeList.add(bosseObj); //删除boss物体
                    bosseObj = null;    //删除boss对象
                    //若重新开始，则在下一次绘制之前将碰撞的物体从GameObjList集合中删除
                    GameUtils.GameObjList.removeAll(GameUtils.removeList);
                    repaint();
                }
            }
        });
        //背景图不断下移，需要重复调用paint方法
        while(true){
            if (state == 1){
                createObj();//当游戏状态处于游戏中时，才能批量创建敌机和子弹
                repaint();
            }
            try {
                Thread.sleep(25);   //一秒钟这个repaint方法会调用40次
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }   //但是加上while循环之后窗口中的文字会出现闪烁的问题，这是因为窗口中的元素都是绘制出来的，每一次循环都要重新绘制一遍
        //在这里我们使用缓存的方式来解决元素闪烁的问题，大致思路是先重新创建一个空的图片，把所有元素先绘制在空的图片上，把绘制好的图片一次性绘制到主窗口中，这被称为双缓存技术

    }

    @Override
    //重写paint()方法，用画笔g绘制桌面窗口背景图片
    public void paint(Graphics g){
        //初始化双缓存对象、
        if (offScreenImage == null){
            offScreenImage = createImage(width,height);
        }
        //获取offScreenImage的画笔对象
        Graphics gImage = offScreenImage.getGraphics();
        //然后填充出一个宽600长600的区域
        gImage.fillRect(0,0,600,600);

        //游戏的初始页面
        if (state == 0){
            gImage.drawImage(GameUtils.bgImg,0,0,null);
            gImage.drawImage(GameUtils.bossImg,220,120,null);
            gImage.drawImage(GameUtils.explodeImg,270,350,null);

            GameUtils.drawWord(gImage,"点击开始游戏",Color.yellow,40,180,300);
            /*//设置字体颜色
            gImage.setColor(Color.yellow);
            //设置字体样式
            gImage.setFont(new Font("仿宋",Font.BOLD,40));
            gImage.drawString("点击开始游戏",180,300);*/
        }
        //游戏开始
        if (state == 1){
            /*g.drawImage(GameUtils.bgImg,0,0,null);
            g.drawImage(GameUtils.bossImg,220,120,null);
            g.drawImage(GameUtils.explodeImg,270,350,null);
            //设置字体颜色
            g.setColor(Color.yellow);
            //设置字体样式
            g.setFont(new Font("仿宋",Font.BOLD,40));
            g.drawString("游戏开始",220,300);*/

          /*  bgObj.paintSelf(gImage);
            //让飞机的对象调用自己的paintSelf()方法,将我方飞机元素绘制进预先规划出的区域中
            planeObj.paintSelf(gImage);*/

            /*//让子弹对象调用自己的paintSelf()方法，将自己绘制进预先的画面中
            shellObj.paintSelf(gImage);*/
            GameUtils.GameObjList.addAll(GameUtils.explodeList);
            for (int i = 0;i < GameUtils.GameObjList.size();i++){
                GameUtils.GameObjList.get(i).paintSelf(gImage);
            }
            //若元物体碰撞，则在下一次绘制之前将碰撞的物体从GameObjList集合中删除
            GameUtils.GameObjList.removeAll(GameUtils.removeList);
        }

        if (state == 3){
            gImage.drawImage(GameUtils.explodeImg,planeObj.getX()-35,planeObj.getY()-50,null);

            GameUtils.drawWord(gImage,"游戏失败",Color.red,50,200,250);
            GameUtils.drawWord(gImage,"点击重玩",Color.red,50,200,350);
            /*//设置字体颜色
            gImage.setColor(Color.red);
            //设置字体样式
            gImage.setFont(new Font("仿宋",Font.BOLD,50));
            gImage.drawString("游戏失败",180,300);*/
        }

        if (state == 4){
            gImage.drawImage(GameUtils.explodeImg,bosseObj.getX()+30,bosseObj.getY()-50,null);

            GameUtils.drawWord(gImage,"游戏通关",Color.red,50,200,250);
            GameUtils.drawWord(gImage,"点击重玩",Color.red,50,200,350);
        }

        //将计分面板绘制在窗口上
        GameUtils.drawWord(gImage,score+"分",Color.green,30,30,100);

        //最后把新图片一次性绘制到主窗口中
        g.drawImage(offScreenImage,0,0,null);
        //每次重绘，count自增
        count++;
       // System.out.println(GameUtils.GameObjList.size());
    }

    //批量创建子弹和敌机的方法
    public void createObj(){
        //控制子弹批量生产的速度，我使用count来控制
        if (count % 10 == 0){
            //通过匿名类的方式创建子弹对象，并添加到shellObList中
            GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,planeObj.getX()+4,planeObj.getY()-16,14,29,5,this));
            //通过索引获取shellObjList最后一个元素，将这个元素添加到GameObjList中
            GameUtils.GameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));

            GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,planeObj.getX()+24,planeObj.getY()-16,14,29,5,this));
            GameUtils.GameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));

            GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,planeObj.getX()-16,planeObj.getY()-16,14,29,5,this));
            GameUtils.GameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));
            /*//键盘输入改变子弹脚本，所以要添加键盘监听事件
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    //子弹*3
                                if (e.getKeyCode() == 32){
                                    GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,planeObj.getX()+4,planeObj.getY()-16,14,29,5,this));
                                    GameUtils.GameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));

                                    GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,planeObj.getX()+24,planeObj.getY()-16,14,29,5,this));
                                    GameUtils.GameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));

                                    GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg,planeObj.getX()-16,planeObj.getY()-16,14,29,5,this));
                                    GameUtils.GameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size()-1));
                                }

                }
            });*/
        }
        //批量创建敌机
        if (count % 15 == 0){
            //通过匿名类的方式创建敌方战机对象，并添加到enemyObList中,敌方飞机大小50左右，所以它的随机位置应该是0-600（不包括0和600，共有12个位置）之间是50倍数的位置
            GameUtils.enemyObjList.add(new EnemyObj(GameUtils.enemyImg,(int)(Math.random()*12)*50,0,49,36,5,this));
            //通过索引获取enemyObjList最后一个元素，将这个元素添加到GameObjList中
            GameUtils.GameObjList.add(GameUtils.enemyObjList.get(GameUtils.enemyObjList.size()-1));
            enemyCount++;
        }

        //批量创建敌机子弹
        if (count % 15 == 0 && bosseObj != null){
            //通过匿名类的方式创建敌方战机对象，并添加到bulletObList中
            GameUtils.bulletObjList.add(new BulletObj(GameUtils.bulletImg,bosseObj.getX()+76,bosseObj.getY()+85,15,25,5,this));
            //通过索引获取bulletObjList最后一个元素，将这个元素添加到GameObjList中
            GameUtils.GameObjList.add(GameUtils.bulletObjList.get(GameUtils.bulletObjList.size()-1));
        }

        if (enemyCount > 100 && bosseObj == null){
            bosseObj = new BossObj(GameUtils.bossImg,250,35,155,100,5,this);
            GameUtils.GameObjList.add(bosseObj);
        }

    }

    //创建一个main方法，获取窗口对象，调用launch方法创建窗口
    public static void main(String[] args){
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }


}
