package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.BombermanGame.*;

public class Brick extends Entity {

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    protected boolean hasItemAddBomb;

    public boolean isHasItemAddBomb() {
        return hasItemAddBomb;
    }

    public void setHasItemAddBomb(boolean hasItemAddBomb) {
        this.hasItemAddBomb= hasItemAddBomb;
    }

    protected boolean hasFlameItem;

    public boolean isHasFlameItem() {
        return hasFlameItem;
    }

    public void setHasFlameItem(boolean hasFlameItem) {
        this.hasFlameItem = hasFlameItem;
    }

    private boolean destroyed;

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    protected boolean checkItemApear;

    public boolean isCheckItemApear() {
        return checkItemApear;
    }

    public void setCheckItemApear(boolean checkItemApear) {
        this.checkItemApear = checkItemApear;
    }

    private int timeToDestroy = 40;

    @Override
    public void update() {
        if(isDestroyed()) {
            if(timeToDestroy-- >0) {
                animate();
                setImg(Sprite.movingSprite(Sprite.brick_exploded,Sprite.brick_exploded1,Sprite.brick_exploded2,animate,40).getFxImage());
            } else{
                setImg(null);
                checkItemApear = true;
            }
        }
    }
}
