package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.positionX;
import static uet.oop.bomberman.BombermanGame.positionY;

public class Balloom extends Entity {

    public int checkPosOfBalloomX[] = {3, Sprite.SCALED_SIZE - 1, 3, Sprite.SCALED_SIZE - 1};
    public int checkPosOfBalloomY[] = {2, 2, Sprite.SCALED_SIZE - 3, Sprite.SCALED_SIZE - 3};

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
    }

    public final int velocityOfBalloom = 3;

    private int timeTransferOfBalloom = 26;

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
        Random random = new Random();
        direction = random.nextInt(20);
        // -- 0:right - 1:left - 2:up - 3:down -- //
        int count = 0;
        switch (direction) {
            case 0: case 1: case 2: case 3: case 4:
            {
                this.x += velocityOfBalloom;
                if (checkCollisionOfBalloom()) {
                    this.x -= velocityOfBalloom;
                }
                setImg(Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, timeTransferOfBalloom).getFxImage());
                break;
            }
            case 5: case 6: case 7: case 8: case 9:
                this.x -= velocityOfBalloom;
                if (checkCollisionOfBalloom()) {
                    this.x += velocityOfBalloom;
                }
                setImg(Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, timeTransferOfBalloom).getFxImage());

                break;
            case 10: case 11: case 12: case 13: case 14:
                this.y -= velocityOfBalloom;
                if (checkCollisionOfBalloom()) {
                    this.y += velocityOfBalloom;
                }
                break;
            case 15: case 16: case 17: case 18: case 19:
                this.y += velocityOfBalloom;
                if (checkCollisionOfBalloom()) {
                    this.y -= velocityOfBalloom;
                }
                break;
            default:
                break;
        }
    }

    public boolean checkCollisionOfBalloom() {
        //check with wall
        for (int i = 0; i < 4; i++) {
            int checkOfBalloomX = (getX() + checkPosOfBalloomX[i]) / Sprite.SCALED_SIZE;
            int checkOfBalloomY = (getY() + checkPosOfBalloomY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkOfBalloomX, checkOfBalloomY);
            if (e instanceof Wall || e instanceof Brick) {
                return true;
            }
        }
        return false;
    }
}
