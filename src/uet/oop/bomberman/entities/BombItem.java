package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public class BombItem extends Entity {

    protected final int bombQty = 2;

    protected int bombIsPlace = bombQty;

    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        placeBomb();
    }

    public void placeBomb() {
        if (space && bombIsPlace > 0) {
            Entity object = null;
            object = new BombItem(bomberman.getYUnit(), bomberman.getXUnit(), Sprite.bomb.getFxImage());
            bomb_items.add(object);
            bombIsPlace--;
        }
    }
}
