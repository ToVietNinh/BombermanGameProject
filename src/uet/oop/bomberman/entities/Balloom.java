package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.positionX;
import static uet.oop.bomberman.BombermanGame.positionY;

public class Balloom extends Entity {

    public int checkPosOfBalloomX[] = {5, Sprite.SCALED_SIZE - 5, 5, Sprite.SCALED_SIZE - 5};
    public int checkPosOfBalloomY[] = {5, 5, Sprite.SCALED_SIZE - 5, Sprite.SCALED_SIZE - 5};

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
    }

    public final int velocityOfBalloom = 1;

    private int timeTransferOfBalloom = 60;

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
        switch (direction) {
            case 0:
            {
                tempX += velocityOfBalloom;
                if(checkCollisionOfBalloom()){
                    tempX -= velocityOfBalloom;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, timeTransferOfBalloom).getFxImage());
                break;
            }
            case 1:

                tempX -= velocityOfBalloom;
                if(checkCollisionOfBalloom()){
                    tempX += velocityOfBalloom;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, timeTransferOfBalloom).getFxImage());

                break;
            case 2:

                tempY -= velocityOfBalloom;
                if(checkCollisionOfBalloom()){
                    tempY += velocityOfBalloom;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setY(tempY);
                break;
            case 3:

                tempY += velocityOfBalloom;

                if(checkCollisionOfBalloom()){
                    tempY -= velocityOfBalloom;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setY(tempY);
                break;
        }

    }

    public boolean checkCollisionOfBalloom() {
        //check with wall
        for (int i = 0; i < 4; i++) {
            int checkOfBalloomX = (tempX + checkPosOfBalloomX[i]) / Sprite.SCALED_SIZE;
            int checkOfBalloomY = (tempY + checkPosOfBalloomY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkOfBalloomX, checkOfBalloomY);
            if (e instanceof Wall || e instanceof Brick) {
                return true;
            }
        }
        return false;
    }
}
