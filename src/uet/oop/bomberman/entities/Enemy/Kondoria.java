package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.BombItem;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Kondoria extends Entity {

    public int checkPosOfKondoriaX[] = {1, Sprite.SCALED_SIZE - 1, 1, Sprite.SCALED_SIZE - 1};
    public int checkPosOfKondoriaY[] = {1, 1, Sprite.SCALED_SIZE - 1, Sprite.SCALED_SIZE - 1};

    public Kondoria(int x, int y, Image img) {
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

    public final int velocityOfKondoria = 2;

    private int timeTransferOfKondoria = 60;

    protected int tempX = this.getX(), tempY = this.getY();

    protected int direction;

    private int timeToDisappearKondoria = 80;

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
            if(timeToDisappearKondoria-- >0) {
                setImg(Sprite.kondoria_dead.getFxImage());
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
                tempX += velocityOfKondoria;
                if(checkCollisionOfKondoria()){
                    tempX -= velocityOfKondoria;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, timeTransferOfKondoria).getFxImage());
                break;
            }
            case 1:
                switchMove++;
                tempX -= velocityOfKondoria;
                if(checkCollisionOfKondoria() ){
                    tempX += velocityOfKondoria;
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                if(switchMove > pointToSwitch){
                    Random random = new Random();
                    setDirection(random.nextInt(4));
                }
                setX(tempX);
                setImg(Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, timeTransferOfKondoria).getFxImage());

                break;
            case 2:
                switchMove++;
                tempY -= velocityOfKondoria;
                if(checkCollisionOfKondoria() ){
                    tempY += velocityOfKondoria;
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
                tempY += velocityOfKondoria;

                if(checkCollisionOfKondoria()){
                    tempY -= velocityOfKondoria;
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

    public boolean checkCollisionOfKondoria() {
        //check with wall
        for (int i = 0; i < 4; i++) {
            int checkOfKondoriaX = (tempX + checkPosOfKondoriaX[i]) / Sprite.SCALED_SIZE;
            int checkOfKondoriaY = (tempY + checkPosOfKondoriaY[i]) / Sprite.SCALED_SIZE;
            Entity e = getEntityInCoordination(checkOfKondoriaX, checkOfKondoriaY);
            if (e instanceof Wall || e instanceof Brick || e instanceof BombItem) {
                return true;
            }
        }
        return false;
    }



//    public class Oneal extends Enemy {
//        private int[] Dx = {-1, 0, 1, 0};
//        private int[] Dy = {0, -1, 0, 1};
//        private int[][] f = new int[Setting.MAX_HEIGHT][Setting.MAX_WIDTH];
//
//        public Oneal(double x, double y, Image img) {
//            super(x, y, img, Setting.timeEnemyDestroy);
//            dx = 0;
//            dy = 0;
//            speed = 0.05;
//        }
//
//        @Override
//        public void update() {
//            Bfs();
//            findPath();
//            super.update();
//            if (getIsDestroy()) {
//                img.setImage(Sprite.oneal_dead.getFxImage());
//            }
//        }
//
//        public boolean move() {
//            if (dx == 1 || dy == 1) {
//                Animation(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, 30);
//
//            }
//            else {
//                Animation(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, 30);
//            }
//            return super.move();
//        }
//
//        public void init() {
//            for (int x = 0; x < Setting.L; ++x) {
//                for (int y = 0; y < Setting.R; ++y) {
//                    f[x][y] = -1;
//                }
//            }
//        }
//        public void findPath(){
//            int intX = (int) x;
//            int intY = (int) y;
//            int X;
//            int Y;
//            if (checkInt()) {
//                if (intX + 1 - x < speed) {
//                    intX = intX + 1;
//                }
//                if (intY + 1 - y < speed) {
//                    intY = intY + 1;
//                }
//                if (f[intX][intY] != -1) {
//                    speed=0.05;
//                    for (int i = 0; i < 4; ++i) {
//                        X = intX + Dx[i];
//                        Y = intY + Dy[i];
//                        if (checkMove(X, Y) && f[intX][intY] == f[X][Y] + 1) {
//                            dx = Dx[i];
//                            dy = Dy[i];
//                        }
//                    }
//                }else{
//                    speed=0.02;
//                    randomMove();
//                }
//            }
//        }
//        public void Bfs() {
//            init();
//            Queue<Point> queue = new LinkedList<>();
//            int floorX = (int) Math.floor(MyList.player.getX());
//            int floorY = (int) Math.floor(MyList.player.getY());
//            int ceilX = (int) Math.ceil(MyList.player.getX());
//            int ceilY = (int) Math.ceil(MyList.player.getY());
//            if (checkMove(floorX, floorY)) {
//                queue.add(new Point(floorX, floorY));
//                f[floorX][floorY] = 1;
//            }
//            if (checkMove(floorX, ceilY)) {
//                queue.add(new Point(floorX, ceilY));
//                f[floorX][ceilY] = 1;
//            }
//            if (checkMove(ceilX, floorY)) {
//                queue.add(new Point(ceilX, floorY));
//                f[ceilX][floorY] = 1;
//            }
//            if (checkMove(ceilX, ceilY)) {
//                queue.add(new Point(ceilX, ceilY));
//                f[ceilX][ceilY] = 1;
//            }
//            Point u;
//            Point v;
//            while (!queue.isEmpty()) {
//                u = queue.poll();
//                for (int i = 0; i < 4; ++i) {
//                    v = new Point(u.getX() + Dx[i], u.getY() + Dy[i]);
//                    if (!checkMove(v.getX(), v.getY())) {
//                        continue;
//                    }
//                    if (f[v.getX()][v.getY()] == -1) {
//                        f[v.getX()][v.getY()] = f[u.getX()][u.getY()] + 1;
//                        queue.add(v);
//                    }
//                }
//            }
//        }
//    }
}
