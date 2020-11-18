package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends Entity {

    protected final int timeTransfer = 26;
    protected int velocity = 3;

    public int collisionX[] = {0, Sprite.SCALED_SIZE - 10, 0, Sprite.SCALED_SIZE - 10};
    public int collisionY[] = {2, 2, Sprite.SCALED_SIZE -5, Sprite.SCALED_SIZE -5};

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        move();
    }

    public void move() {
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
}
