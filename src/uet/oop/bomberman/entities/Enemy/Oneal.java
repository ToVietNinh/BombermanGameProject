package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.BombItem;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends Entity {

    public int checkPosOfOnealX[] = {1, Sprite.SCALED_SIZE - 1, 1, Sprite.SCALED_SIZE - 1};
    public int checkPosOfOnealY[] = {1, 1, Sprite.SCALED_SIZE - 1, Sprite.SCALED_SIZE - 1};

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    protected boolean checkDied;

    public boolean isCheckDied() {
        return checkDied;
    }

    public void setCheckDied(boolean checkDied) {
        this.checkDied = checkDied;
    }

    protected final int pointToSwitch = 100;

    protected int switchMove=0;

    public final int velocityOfOneal = 2;

    private int timeTransferOfOneal = 60;

    protected int tempX = this.getX(), tempY = this.getY();

    protected int direction;

    private int timeToDisappearOneal = 80;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void update() {
        if(!isCheckDied()) {
            animate();
            move();
        }
        else {
            if(timeToDisappearOneal-- >0) {
                setImg(Sprite.oneal_dead.getFxImage());
            }
            else {
                setImg(null);
            }
        }
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
