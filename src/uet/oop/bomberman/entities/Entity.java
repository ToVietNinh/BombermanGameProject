package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public abstract class Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 5000;
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    public int getXUnit() {
        return x / Sprite.SCALED_SIZE ;
    }

    public int getX() {
        return x ;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getYUnit() {
        return y / Sprite.SCALED_SIZE ;
    }

    public int getY() {
        return y ;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity(){

    }

    public void animate(){
        if(animate > MAX_ANIMATE) {
            animate = 0;
        }
        else {
            animate += 1;
        }
    }


    public static Entity getEntityInCoordination(int x, int y){
        for(Entity e : walls) {
            if(e.getXUnit() == x && e.getYUnit() == y){
                return e;
            }
        }
        for(Entity e : bricks){
            if(e.getXUnit() == x && e.getYUnit() == y) {
                return e;
            }
        }
        for(Entity e : ballooms) {
            if(e.getXUnit() == x && e.getYUnit() == y) {
                return e;
            }
        }
        for(Entity e : oneals) {
            if(e.getXUnit() == x && e.getYUnit() == y) {
                return e;
            }
        }
        for(Entity e : dolls) {
            if(e.getXUnit() == x && e.getYUnit() == y) {
                return e;
            }
        }
        for(Entity e : kondorias) {
            if(e.getXUnit() == x && e.getYUnit() == y) {
                return e;
            }
        }
        for(Entity e : bomb_items) {
            if(e.getXUnit() == x && e.getYUnit() == y) {
                return e;
            }
        }
        if(bomberman.getXUnit() == x && bomberman.getYUnit() == y) {
            return bomberman;
        }
        if(portal != null) {
            if (portal.getXUnit() == x && portal.getYUnit() == y) {
                return portal;
            }
        }
        return null;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();

}
