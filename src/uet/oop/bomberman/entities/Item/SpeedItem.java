package uet.oop.bomberman.entities.Item;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.BombItem;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Brick.*;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.entities.Bomber.*;

public class SpeedItem extends Entity {
    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    protected int count = 0;

    @Override
    public void update() {
        if ((bomberman.getX() + 16) / Sprite.SCALED_SIZE == (getX() + 16) / Sprite.SCALED_SIZE
                && (bomberman.getY() + 16) / Sprite.SCALED_SIZE == (getY() + 16) / Sprite.SCALED_SIZE && count < 1) {
            setImg(null);
            count++;
           Bomber.setVelocity(Bomber.getVelocity()+1);
        }
    }
}
