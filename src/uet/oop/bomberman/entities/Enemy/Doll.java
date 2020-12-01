package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.BombItem;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Entity {

    public int checkPosOfDollX[] = {1, Sprite.SCALED_SIZE - 1, 1, Sprite.SCALED_SIZE - 1};
    public int checkPosOfDollY[] = {1, 1, Sprite.SCALED_SIZE - 1, Sprite.SCALED_SIZE - 1};

    public Doll(int x, int y, Image img) {
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

    public final int velocityOfDoll = 3;

    private int timeTransferOfDoll = 60;

    protected int tempX = this.getX(), tempY = this.getY();

    protected int direction;

    private int timeToDisappearDoll= 80;

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
            if(timeToDisappearDoll-- >0) {
                setImg(Sprite.doll_dead.getFxImage());
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
                tempX += velocityOfDoll;
                if(checkCollisionOfDoll()){
                    tempX -= velocityOfDoll;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, timeTransferOfDoll).getFxImage());
                break;
            }
            case 1:
                switchMove++;
                tempX -= velocityOfDoll;
                if(checkCollisionOfDoll() ){
                    tempX += velocityOfDoll;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left2, animate, timeTransferOfDoll).getFxImage());

                break;
            case 2:
                switchMove++;
                tempY -= velocityOfDoll;
                if(checkCollisionOfDoll() ){
                    tempY += velocityOfDoll;
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
                tempY += velocityOfDoll;

                if(checkCollisionOfDoll()){
                    tempY -= velocityOfDoll;
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

    public boolean checkCollisionOfDoll() {
        //check with wall
        for (int i = 0; i < 4; i++) {
            int checkOfDollX = (tempX + checkPosOfDollX[i]) / Sprite.SCALED_SIZE;
            int checkOfDollY = (tempY + checkPosOfDollY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkOfDollX, checkOfDollY);
            if (e instanceof Wall || e instanceof Brick || e instanceof BombItem) {
                return true;
            }
        }
        return false;
    }
}
