package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

import static uet.oop.bomberman.entities.BombItem.*;

public class Bomber extends Entity {

    public int isMoving;
    protected final int timeTransfer = 26;
    protected int velocity = 2;
    protected int powerSpeed = 3;
    protected int count = 1;
    protected boolean movedOutBomb = true;
    protected int spaceToMoveOut = 32;
    protected int timeToDisappear = 200;
    public boolean checkLeft, checkRight, checkUp, checkDown;

    protected boolean checkDied;

    public boolean isCheckDied() {
        return checkDied;
    }

    public void setCheckDied(boolean checkDied) {
        this.checkDied = checkDied;
    }

    public int collisionX[] = {0, Sprite.SCALED_SIZE - 10, 0, Sprite.SCALED_SIZE - 10};
    public int collisionY[] = {2, 2, Sprite.SCALED_SIZE - 5, Sprite.SCALED_SIZE - 5};

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(!isCheckDied()) {
            animate();
            move();
            /*if (checkCollisionDead()) {
                //bomberman.setImg(Sprite.movingSprite(Sprite.player_dead1,Sprite.player_dead2,Sprite.player_dead3,animate,timeTransfer).getFxImage());
                bomberman.setX(Sprite.SCALED_SIZE);
                bomberman.setY(Sprite.SCALED_SIZE);
                positionX = bomberman.getX();
                positionY = bomberman.getY();
                bomberman.setImg(Sprite.player_right.getFxImage());
            }*/
            placeBomb();
        } else {
            if(timeToDisappear-- >0) {
                animate();
                setImg(Sprite.movingSprite(Sprite.player_dead1,Sprite.player_dead2,Sprite.player_dead3,animate,timeTransfer).getFxImage());
            }
            else {
                setImg(null);
            }
        }
    }

    public void move() {


        if (goLeft) {
            if (shift) positionX -= powerSpeed;
            else if (shift == false) positionX -= velocity;
            if (checkCollision()) {
                if (shift) positionX += powerSpeed;
                else if (shift == false) positionX += velocity;
            }
            bomberman.setX(positionX);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, animate, timeTransfer).getFxImage());

        }


        if (goRight) {
            if (shift) positionX += powerSpeed;
            else if (shift == false) positionX += velocity;
            if (checkCollision()) {
                if (shift) positionX -= powerSpeed;
                else if (shift == false) positionX -= velocity;
            }
            bomberman.setX(positionX);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, animate, timeTransfer).getFxImage());
        }


        if (goUp) {
            if (shift) positionY -= powerSpeed;
            else if (shift == false) positionY -= velocity;
            if (checkCollision()) {
                if (shift) positionY += powerSpeed;
                else if (shift == false) positionY += velocity;
            }
            bomberman.setY(positionY);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, animate, timeTransfer).getFxImage());

        }
        if (goDown) {
            if (shift) positionY += powerSpeed;
            else if (shift == false) positionY += velocity;
            if (checkCollision()) {
                if (shift) positionY -= powerSpeed;
                else if (shift == false) positionY -= velocity;
            }
            bomberman.setY(positionY);
            bomberman.setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, animate, timeTransfer).getFxImage());

        }

    }


    public boolean checkCollision() {
        boolean checkTemp;

        //check with wall, brick, bombitem
        for (int i = 0; i < 4; i++) {
            int checkX = (positionX + collisionX[i]) / Sprite.SCALED_SIZE;
            int checkY = (positionY + collisionY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkX, checkY);
            if (e instanceof Wall || e instanceof Brick) {
                return true;
            }


            /* (e instanceof BombItem ) {
                return true;
            }*/
        }
        /*if(checkBomberInBombItem() && bomb_items.size() >0) {
            return false;
        }*/
        return false;
    }

    public boolean checkCollisionDead() {
        /*for (int i = 0; i < 4; i++) {
            int checkDeadX = (this.getX() + collisionX[i]) / Sprite.SCALED_SIZE;
            int checkDeadY = (this.getY() + collisionY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkDeadX, checkDeadY);
            if (e instanceof Balloom) {
                return true;
            }
        }*/
        return false;
    }



    public boolean checkBomberInBombItem() {
        for (Entity e : bomb_items) {
            if (getX() + Sprite.SCALED_SIZE +5 < e.getX() || getX() > e.getX() + Sprite.SCALED_SIZE-10
                    || getY() + Sprite.SCALED_SIZE -10 < e.getY() || getY() > e.getY() + Sprite.SCALED_SIZE -10) {
                return false;
            }

            /*if ((e.getX() <= getX() + 32 && getY() + 32 >= e.getY() && getY() <= e.getY() + 32)
                    || (e.getX() + 32 >= getX() && getY() + 32 >= e.getY() && getY() <= e.getY() + 32)
                    || (e.getY() <= getY() + 32 && getX() + 32 >= e.getX() && getX() <= e.getX() + 32)
                    || (e.getY() + 32 >= getY() && getX() + 32 >= e.getX() && getX() <= e.getX() + 32))*/

        }
        return true;
    }

    public void placeBomb() {
        if (space && bomb_count < 1 && bombQty > 0) {

            Entity object = null;
            bomb_count++;
            object = new BombItem((bomberman.getX()+16) / Sprite.SCALED_SIZE, (bomberman.getY()+16) / Sprite.SCALED_SIZE);
            bomb_items.add(object);
            //((BombItem) object).loadFrame();
            //((BombItem) object) .explodeBombFunctionFrame();
            //System.out.println(count++);
            //object.setImg(null);
            /*if(((BombItem) object).checkExplode){
                System.out.println("aaa");
               bomb_items.get(bomb_items.indexOf(object)).setImg(null);
               //bomb_items.remove(object);
            }*/
            bombQty--;
            checkTemp = false;
        }
        if (space == false) bomb_count = 0;

    }

}
