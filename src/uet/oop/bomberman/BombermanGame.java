package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Item.AddBombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.BombItem.*;
import uet.oop.bomberman.entities.Brick.*;

import java.io.*;
import java.util.*;

import static uet.oop.bomberman.entities.BombItem.*;


public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> walls = new ArrayList<>();
    public static List<Entity> bricks = new ArrayList<>();
    public static List<Entity> ballooms = new ArrayList<>();
    public static List<Entity> oneals = new ArrayList<>();
    public static List<Entity> bomb_items = new ArrayList<>();
    public static List<Entity> speed_items = new ArrayList<>();
    public static List<Entity> flame_items = new ArrayList<>();
    public static List<Entity> addBomb_items = new ArrayList<>();

    public static Entity bomberman;
    public static Entity bombItem;
    public static boolean goUp, goDown, goLeft, goRight, space, isMoving, shift, alt;
    public static int positionX, positionY;
    public static boolean checkPlaceMore, checkTemp;
    public char[][] contentFileLever1 = new char[14][32];
    protected int count = 1;




    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setFill(Color.rgb(80, 160, 0));

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()) {
                            case UP:
                                goUp = true;
                                isMoving = true;
                                break;
                            case DOWN:
                                goDown = true;
                                isMoving = true;
                                break;
                            case LEFT:
                                goLeft = true;
                                isMoving = true;
                                break;
                            case RIGHT:
                                goRight = true;
                                isMoving = true;
                                break;
                            case SPACE:
                                space = true;

                                break;
                            case SHIFT:
                                shift = true;
                                break;
                            case ALT:
                                alt = true;
                                break;
                        }
                    }
                }
        );
        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()) {
                            case UP:
                                goUp = false;
                                isMoving = false;
                                break;
                            case DOWN:
                                goDown = false;
                                isMoving = false;
                                break;
                            case LEFT:
                                goLeft = false;
                                isMoving = false;
                                break;
                            case RIGHT:
                                goRight = false;
                                isMoving = false;
                                break;
                            case SPACE:
                                space = false;
                                break;
                            case SHIFT:
                                shift = false;
                                break;
                            case ALT:
                                alt = false;
                                break;
                        }
                    }
                }
        );

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                update();
                renderEntities();

            }

        };

        //renderStillObject();

        timer.start();

        try {
            createMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        positionX = bomberman.getX();
        positionY = bomberman.getY();

        //bombItem = new BombItem(1,3,Sprite.bomb.getFxImage());
        //bomb_items.add(new BombItem(3,2,Sprite.bomb.getFxImage()));
        for(Entity e : bomb_items) {
            bombItem.update();
        }
    }

    //FileInputStream fileInputStream = new FileInputStream("mapLevel1.txt");
    public void createMap() throws Exception {
        FileReader fr = new FileReader("C:/Users/Admin/Desktop/bomberman_project/res/levels/mapLevel1.txt");
        BufferedReader br = new BufferedReader(fr);
        for (int i = 0; i < HEIGHT; i++) {
            String line = br.readLine();
            for (int j = 0; j < WIDTH; j++) {
                contentFileLever1[i][j] = line.charAt(j);

            }
        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                if (contentFileLever1[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    walls.add(object);
                } else if (contentFileLever1[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    // addBombItem : (2,7)
                    if((i==2 && j==7 )||( i==1 && j==10) || (i==7 && j==14) || (i==1 && j==7) ){
                        ((Brick) object).setHasItemAddBomb(true);
                    }
                    else if((i==2 && j==13 )||( i==2 && j==23) || (i==11 && j==16) ){
                        ((Brick) object).setHasFlameItem(true);

                    }
                    bricks.add(object);
                } else if (contentFileLever1[i][j] == '1') {
                    object = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
                    ballooms.add(object);
                } else if (contentFileLever1[i][j] == '2') {
                    object = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    oneals.add(object);
                }

            }
        }
        for(int i=0; i<bricks.size(); i++) {
            if(i%2 == 0) {
            }
        }

    }



    public void update() {
        walls.forEach(Entity::update);
        bomberman.update();
        ballooms.forEach(Entity::update);
        oneals.forEach(Entity::update);
        bomb_items.forEach(Entity::update);
        bricks.forEach(Entity::update);
        addBomb_items.forEach(Entity::update);
        flame_items.forEach(Entity::update);

        for(Entity e : bricks) {
            if(((Brick) e).isDestroyed() && ((Brick) e).isHasItemAddBomb() && ((Brick) e).isCheckItemApear()) {
                addBomb_items.add(new AddBombItem(e.getXUnit(),e.getYUnit(),Sprite.powerup_bombs.getFxImage()));
            }
            else if(((Brick) e).isDestroyed() && ((Brick) e).isHasFlameItem() && ((Brick) e).isCheckItemApear()){
                flame_items.add(new FlameItem(e.getXUnit(),e.getYUnit(),Sprite.powerup_flames.getFxImage()));
            }
        }



        removeEntityDisappeared();
    }

    public void renderEntities() {
        //gc.clearRect(bomberman.getX()-2, bomberman.getY()-3, 32, 39);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        bomberman.render(gc);
        /*for(Entity eBalloom: ballooms){
            gc.clearRect(eBalloom.getX()-2 , eBalloom.getY()-3,37,39);
        }*/
        ballooms.forEach(g -> g.render(gc));
        oneals.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
        addBomb_items.forEach(g -> g.render(gc));
        flame_items.forEach(g -> g.render(gc));
//        flame_items.forEach(g -> g.render(gc));
//        speed_items.forEach(g -> g.render(gc));
        //bomb_items.forEach(g -> g.render(gc));

        for(Entity e : bomb_items) {
            if(((BombItem) e).explored){
                ((BombItem) e).frameRender(gc);
            }
            else {
                e.render(gc);
            }
        }



    }

    public void renderStillObject() {
        //walls.forEach(g -> g.render(gc));
    }

    public void removeEntityDisappeared() {
        for(Entity e : bomb_items) {
            if(e.getImg() == null) {
                bomb_items.remove(e);
                break;

            }
        }

        for(Entity e : ballooms) {
            if(e.getImg() == null) {
                ballooms.remove(e);
                break;

            }
        }

        for(Entity e : oneals) {
            if(e.getImg() == null) {
                oneals.remove(e);
                break;
            }
        }
        if(bomberman.getImg()==null) {

        }

        for(Entity e : bricks) {
            if(e.getImg() == null) {
                bricks.remove(e);
                break;
            }
        }

        for(Entity e : addBomb_items) {
            if(e.getImg() == null) {
                addBomb_items.remove(e);
                break;
            }
        }
    }

}
