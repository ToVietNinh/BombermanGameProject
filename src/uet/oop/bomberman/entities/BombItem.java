package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public class BombItem extends Entity {

    protected static int bombQty = 3;

    protected static int bomb_count = 0;

    protected int timeTransferOfBomb = 26;

    protected int frameBomb = 3;

    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }


    @Override
    public void update() {
        animate();
        explodeBomb();
    }

    public void explodeBomb() {
        if(alt) {
            for(int i=0;i<bomb_items.size();i++){
                while(frameBomb >0){
                    bomb_items.get(i).setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,Sprite.bomb_exploded2,animate,timeTransferOfBomb).getFxImage());
                    frameBomb--;
                }

            }
        }
    }



}
