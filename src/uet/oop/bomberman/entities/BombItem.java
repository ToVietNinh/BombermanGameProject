package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

import uet.oop.bomberman.entities.Enemy.Balloom;
import uet.oop.bomberman.entities.Enemy.Doll;
import uet.oop.bomberman.entities.Enemy.Kondoria;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Panel.InfoPanel;
import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.entities.Bomber.*;


public class BombItem extends Entity {
    private int timeBeforeExplore = 130;
    private int timeFlame = 15;
    private int timeTransfer = 40;
    public boolean explored;
    protected static int flameLen = 1;
    protected static int bomb_count = 0;

    public static int checkTimeToSetNull = 100;

    public static int getFlameLen() {
        return flameLen;
    }

    public static void setFlameLen(int flameLen) {
        BombItem.flameLen = flameLen;
    }

    protected static int bombQty = 1;

    public static int getBombQty() {
        return bombQty;
    }

    public static void setBombQty(int bombQty) {
        BombItem.bombQty = bombQty;
    }

    public void setFlameList(List<Flame> flameList) {
        BombItem.flameList = flameList;
    }

    public static List<Flame> flameList = new ArrayList<>();

    public BombItem(int x, int y) {
        super(x, y, Sprite.bomb.getFxImage());
        this.flameLen = flameLen;
        explored = false;
    }

    @Override
    public void update() {
        animate();
        if (explored == false) {
            if (timeBeforeExplore-- > 0) {
                setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, timeTransfer).getFxImage());
            } else {
                explored = true;
                explosion();
            }
        } else {
            if (timeFlame-- == 0) {
                bombCountWillBePlaced--;
                setImg(null);
                //flameList.clear();
                for(Entity e : flameList ) {
                    e.setImg(null);
                }
            }
        }

    }

    private void explosion() {//init FlameList
        int x = getXUnit();
        int y = getYUnit();

        flameList.add(new Flame(x, y, 4, false));// add center
        //truong hop bomber o tren qua bom
        Entity e = getEntityInCoordination(x,y);
        canPassThrough(e);

        //check left
        int il = 1;
        for (; il <= flameLen; il++) { //check tu 1 den FrameLen neu gap vat can break
            int xLeft = x - il;
            e = getEntityInCoordination(xLeft,y);
            if (!canPassThrough(e)) {
                break;
            }
        }
        for (int i = 1; i < il; i++) { // them flame tu 1 den do dai frame lon nhat co the
            if (i == il - 1) {
                flameList.add(new Flame(x - i, y, 2, true));
            } else {
                flameList.add(new Flame(x - i, y, 2, false));
            }
        }

        //check right
        int ir = 1;
        for (; ir <= flameLen; ir++) {
            int xRight = x + ir;
            e = getEntityInCoordination(xRight,y);
            if (!canPassThrough(e)) {
                break;
            }
        }
        for (int i = 1; i < ir; i++) {
            if (i == ir - 1) {
                flameList.add(new Flame(x + i, y, 3, true));
            } else {
                flameList.add(new Flame(x + i, y, 3, false));
            }
        }

        //check up
        int iu = 1;
        for (; iu <= flameLen; iu++) {
            int yUp = y - iu;
            e = getEntityInCoordination(x,yUp);
            if (!canPassThrough(e)) {
                break;
            }
        }
        for (int i = 1; i < iu; i++) {
            if (i == iu - 1) {
                flameList.add(new Flame(x, y - i, 0, true));
            } else {
                flameList.add(new Flame(x, y - i, 0, false));
            }
        }

        //check down
        int id = 1;
        for (; id <= flameLen; id++) {
            int yDown = y + id;
            e = getEntityInCoordination(x,yDown);
            if (!canPassThrough(e)) {
                break;
            }
        }
        for (int i = 1; i < id; i++) {
            if (i == id - 1) {
                flameList.add(new Flame(x, y + i, 1, true));
            } else {
                flameList.add(new Flame(x, y + i, 1, false));
            }
        }
    }

    public void frameRender(GraphicsContext gc) {
        flameList.forEach(g -> g.render(gc));
    }

    public boolean isExplored() {
        return explored;
    }

    public boolean canPassThrough(Entity e) { // return false if ko truyen qua dc e, true if truyen qua dc
        if (e instanceof Brick) {
            ((Brick) e).setDestroyed(true);

            return false;
        }
        if (e instanceof Wall || e instanceof Portal) {
            return false;
        }
        if (e instanceof Balloom) {
            ((Balloom) e).setCheckDied(true);
            InfoPanel.nowScore += 20;
        }

        if(e instanceof Oneal) {
            ((Oneal) e).setCheckDied(true);
            InfoPanel.nowScore += 50;
        }

        if(e instanceof Doll) {
            ((Doll) e).setCheckDied(true);
            InfoPanel.nowScore += 80;
        }

        if(e instanceof Kondoria) {
            ((Kondoria)e).setCheckDied(true);
            InfoPanel.nowScore += 100;
        }

        if (e instanceof Bomber) {
            ((Bomber) e) .setCheckDied(true);
        }

        return true;
    }

    public List<Flame> getFlameList() {
        return flameList;
    }
}