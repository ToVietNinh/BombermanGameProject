package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Entity {

    public int isMoving;
    protected final int timeTransfer = 26;
    protected int velocity = 2;
    protected int count;

    public boolean checkLeft, checkRight, checkUp, checkDown;

    public int collisionX[] = {0, Sprite.SCALED_SIZE - 10, 0, Sprite.SCALED_SIZE - 10};
    public int collisionY[] = {2, 2, Sprite.SCALED_SIZE - 5, Sprite.SCALED_SIZE - 5};

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        move();
        /*if (checkCollisionDead()) {
            //bomberman.setImg(Sprite.movingSprite(Sprite.player_dead1,Sprite.player_dead2,Sprite.player_dead3,animate,timeTransfer).getFxImage());
            bomberman.setX(1);
            bomberman.setY(1);
            positionX=1;
            positionY =1;
        }*/
    }

    public void move() {
        if (checkCollisionDead()) {
            //bomberman.setImg(Sprite.movingSprite(Sprite.player_dead1,Sprite.player_dead2,Sprite.player_dead3,animate,timeTransfer).getFxImage());
            bomberman.setX(1);
            bomberman.setY(1);
            positionX = 1;
            positionY = 1;
        }
        if (goLeft) {

            positionX -= velocity;
            if (checkCollision()) {
                positionX += velocity;
            }
            bomberman.setX(positionX);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, timeTransfer).getFxImage());

        }


        if (goRight) {

            positionX += velocity;
            if (checkCollision()) {
                positionX -= velocity;
            }
            bomberman.setX(positionX);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, timeTransfer).getFxImage());
        }


        if (goUp) {
            positionY -= velocity;
            if (checkCollision()) {
                positionY += velocity;
            }
            bomberman.setY(positionY);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, timeTransfer).getFxImage());

        }
        if (goDown) {
            positionY += velocity;
            if (checkCollision()) {
                positionY -= velocity;
            }
            bomberman.setY(positionY);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, timeTransfer).getFxImage());

        }

    }


    public boolean checkCollision() {
        //check with wall
        for (int i = 0; i < 4; i++) {
            int checkX = (positionX + collisionX[i]) / Sprite.SCALED_SIZE;
            int checkY = (positionY + collisionY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkX, checkY);
            if (e instanceof Wall || e instanceof Brick) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollisionDead() {
        /*for (int i = 0; i < 4; i++) {
            int checkDeadX = (positionX + collisionX[i]) / Sprite.SCALED_SIZE;
            int checkDeadY = (positionY + collisionY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkDeadX, checkDeadY);
            if (e instanceof Balloom) {
                return true;
            }
        }*/
        return false;
    }



}
