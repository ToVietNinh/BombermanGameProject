package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public class Portal extends Entity {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    protected int count =0;

    @Override
    public void update() {
        if(checkGoToNext()) {
            if ((bomberman.getX() + 16) / Sprite.SCALED_SIZE == (getX() + 16) / Sprite.SCALED_SIZE
                    && (bomberman.getY() + 16) / Sprite.SCALED_SIZE == (getY() + 16) / Sprite.SCALED_SIZE && count < 1) {
                setImg(null);
                count++;

            }
        }
    }

    public static boolean checkGoToNext() {
//        if(ballooms.size() == 2 && oneals.size() == 3) {
//            return true;
//        }
        return true;
    }


}
