package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

import static uet.oop.bomberman.BombermanGame.*;

public class BombItem extends Entity {

    public static int bombQty = 20;

    public static int bomb_count = 0;

    protected int timeTransferOfBomb = 26;

    protected int frameBomb = 20;

    protected int checkUpdateMore =0;

    protected int temp;

    protected boolean checkExplode;

    protected boolean checkToContinue ;

    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }
    int c = 0;
    protected int countToVoidExplodeFrame = 0;

    @Override
    public void update() {
        /*if (getImg() != null){
            if (!checkExplode) {
                updateToLoadframe();
                //animate();
                if (checkToContinue) {
                    //System.out.println(c++);
                    updateToLoadExplodeFrame();
                }
            }
        }*/
        if(checkExplode) {
            System.out.println("aaa");
            setImg(null);
        }
    }

    public void updateToLoadframe() {

        if(bomb_items.size() > temp) {
            checkUpdateMore--;
        }
        if(checkUpdateMore <1) {
            loadFrame();
            checkUpdateMore++;
        }
        temp = bomb_items.size();
    }

    public void updateToLoadExplodeFrame() {
        explodeBombFunctionFrame();


    }

    public void loadFrame() {
        //checkToContinue = false;
        Timer myTimer1 = new Timer();
        myTimer1.schedule(new TimerTask()  {
            @Override
            public void run() {
                Timer myTimer2 = new Timer();
                setImg(Sprite.bomb.getFxImage());
                myTimer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Timer myTimer3 = new Timer();
                        setImg(Sprite.bomb_1.getFxImage());

                        myTimer3.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setImg(Sprite.bomb_2.getFxImage());
                                myTimer1.cancel();
                                myTimer2.cancel();
                                myTimer3.cancel();
                            }
                        },300,100);
                    }
                },300,100);
            }
        },300,200);

        //checkToContinue = true;
    }

    public void explodeBombFunctionFrame() {
        Timer myTimer1 = new Timer();

        myTimer1.schedule(new TimerTask() {
            @Override
            public void run() {
                Timer myTimer2 = new Timer();

                setImg(Sprite.bomb_exploded.getFxImage());
                myTimer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Timer myTimer3 = new Timer();

                        setImg(Sprite.bomb_exploded1.getFxImage());
                        myTimer3.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                checkExplode = true;

                                setImg(Sprite.bomb_exploded2.getFxImage());
                                myTimer1.cancel();
                                myTimer2.cancel();
                                myTimer3.cancel();

                            }
                        },300,200);

                        //setImg(Sprite.movingSprite(Sprite.bomb_exploded,Sprite.bomb_exploded1,Sprite.bomb_exploded2,animate,timeTransferOfBomb).getFxImage());
                    }
                },100);


            }


        },2500);
        //checkExplode = true;


    }

    /*public void explodeBombFrame() {
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                explodeBombFunctionFrame();
                checkExplode = true;
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        myTimer.cancel();
                    }
                },100);
            }
        },1800);
    }*/


}
