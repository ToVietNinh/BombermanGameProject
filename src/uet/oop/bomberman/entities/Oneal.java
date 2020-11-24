package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.positionX;
import static uet.oop.bomberman.BombermanGame.positionY;

public class Oneal extends Entity {

    public int checkPosOfOnealX[] = {1, Sprite.SCALED_SIZE - 1, 1, Sprite.SCALED_SIZE - 1};
    public int checkPosOfOnealY[] = {1, 1, Sprite.SCALED_SIZE - 1, Sprite.SCALED_SIZE - 1};

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    protected final int pointToSwitch = 100;

    protected int switchMove=0;

    public final int velocityOfOneal = 3;

    private int timeTransferOfOneal = 60;

    protected int tempX = this.getX(), tempY = this.getY();

    protected int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void update() {
        animate();
        move();
    }

    public void move(){
        // -- 0:right - 1:left - 2:up - 3:down -- //
        int count = 0;
        if(switchMove>102) switchMove=0;
        switch (direction) {
            case 0:
            {
                switchMove++;
                tempX += velocityOfOneal;
                if(checkCollisionOfOneal()){
                    tempX -= velocityOfOneal;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, timeTransferOfOneal).getFxImage());
                break;
            }
            case 1:
                switchMove++;
                tempX -= velocityOfOneal;
                if(checkCollisionOfOneal() ){
                    tempX += velocityOfOneal;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, timeTransferOfOneal).getFxImage());

                break;
            case 2:
                switchMove++;
                tempY -= velocityOfOneal;
                if(checkCollisionOfOneal() ){
                    tempY += velocityOfOneal;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setY(tempY);
                break;
            case 3:
                switchMove++;
                tempY += velocityOfOneal;

                if(checkCollisionOfOneal()){
                    tempY -= velocityOfOneal;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                Random random = new Random();
                setDirection(random.nextInt(4));
            }
                setY(tempY);
                break;
        }

    }

    public boolean checkCollisionOfOneal() {
        //check with wall
        for (int i = 0; i < 4; i++) {
            int checkOfOnealX = (tempX + checkPosOfOnealX[i]) / Sprite.SCALED_SIZE;
            int checkOfOnealY = (tempY + checkPosOfOnealY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkOfOnealX, checkOfOnealY);
            if (e instanceof Wall || e instanceof Brick || e instanceof BombItem) {
                return true;
            }
        }
        return false;
    }
}
