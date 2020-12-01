package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.Enemy.Balloom;
import uet.oop.bomberman.entities.Enemy.Doll;
import uet.oop.bomberman.entities.Enemy.Kondoria;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Item.AddBombItem;
import uet.oop.bomberman.entities.Item.FlameItem;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Item.SpeedItem;
import uet.oop.bomberman.entities.Panel.MenuGame;
import uet.oop.bomberman.entities.Panel.ShowPanel;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Panel.InfoPanel;

import javax.sound.sampled.Line;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class BombermanGame extends Application {
    static String level1 = "res/levels/mapLevel1.txt";
    static String level2 = "res/levels/mapLevel2.txt";
    static String level3 = "res/levels/mapLevel3.txt";
    public static String[] mapLevel = {level1, level2, level3};

    protected boolean checkToNext;
    protected boolean checkToPassLevel2;
    protected int tempToNextOneTime = 0;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 14;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> walls = new ArrayList<>();
    public static List<Entity> bricks = new ArrayList<>();
    public static List<Entity> ballooms = new ArrayList<>();
    public static List<Entity> oneals = new ArrayList<>();
    public static List<Entity> dolls = new ArrayList<>();
    public static List<Entity> kondorias = new ArrayList<>();
    public static List<Entity> bomb_items = new ArrayList<>();
    public static List<Entity> speed_items = new ArrayList<>();
    public static List<Entity> flame_items = new ArrayList<>();
    public static List<Entity> addBomb_items = new ArrayList<>();


    public static Entity bomberman;
    public static Entity bombItem;
    public static Entity portal = null;
    protected static boolean checkToPlacePortal = true;
    public static boolean goUp, goDown, goLeft, goRight, space, isMoving, shift, alt;
    public static int positionX, positionY;
    public static boolean checkPlaceMore, checkTemp;
    public static char[][] contentFileLever1 = new char[14][31];
    protected int count = 1;
    //protected int timeToExit = 60;
    protected int timeToTransferNewLevel = 120;

    public boolean showMenu = true;
    public boolean mute = false;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        //Tao scene phu
        //Sound.play(Sound.BACKGROUND, 50);
        /*AnchorPane gameOverRoot = new AnchorPane();
        Text text = new Text("Gameover");
        gameOverRoot.getChildren().add(text);
        text.setX(1000);
        text.setY(1000);
        Scene sceneExit = new Scene(gameOverRoot);*/
        Keyboard input = new Keyboard();
        MenuGame menuGame = new MenuGame(input);
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        AnchorPane root = new AnchorPane();
//        root.setPadding(new Insets(15,20, 10,10));
//        TextArea textArea = new TextArea("Score");

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
                        input.updateKeyPressed(event);
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
                        input.updateKeyReleased(event);
                    }
                }
        );

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("BombermanGame");
        stage.show();

        /*try {
            String musicFile = "C:/Users/Admin/Desktop/bomberman_project/Unity - TheFatRat.wav";

            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
        }catch (Exception e){
            System.out.println(e);
        }*/
        //menuGame = new MenuGame();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (showMenu) {
                    menuGame.showMenuGame(gc);
                    menuGame.update();

                    if (menuGame.isQuit()) {
                        stage.close();
                    } else if (menuGame.isStartGame()) {
                        mute = menuGame.isMute();
                        menuGame.setStartGame(false);
                        showMenu = false;
                    }
                } else {
                    update();
                    renderEntities();
                    InfoPanel.showInfoPanel(gc);
                    if (Bomber.checkGameOver || InfoPanel.timeRemain <=0) {
                        this.stop();
                        ShowPanel.renderGameOverScreen(gc);
                    }
                    if (checkToNext && tempToNextOneTime < 1) {
                        resetForNewLevel();
                        try {
                            createMap(mapLevel[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        scene.setFill(Color.rgb(204, 102, 0));
                        checkToPassLevel2 = true;
                        checkToNext = false;
                        tempToNextOneTime++;
                    }
                    if (checkToNext && checkToPassLevel2) {
                        resetForNewLevel();
                        try {
                            checkToPlacePortal = false;
                            createMap(mapLevel[2]);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        scene.setFill(Color.rgb(96, 96, 180));
//                    checkToPassLevel2 = true;
                        checkToNext = false;
                    }
                }

            }

        };

        //renderStillObject();

        timer.start();
        try {
            createMap(mapLevel[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
//        positionX = bomberman.getX();
//        positionY = bomberman.getY();

        //bombItem = new BombItem(1,3,Sprite.bomb.getFxImage());
        //bomb_items.add(new BombItem(3,2,Sprite.bomb.getFxImage()));
        for (Entity e : bomb_items) {
            bombItem.update();
        }
    }

    //FileInputStream fileInputStream = new FileInputStream("mapLevel1.txt");
    public static void createMap(String path) throws Exception {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        for (int i = 0; i < HEIGHT - 1; i++) {
            String line = br.readLine();
            for (int j = 0; j < WIDTH; j++) {
                contentFileLever1[i][j] = line.charAt(j);

            }
        }
        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        positionX = bomberman.getX();
        positionY = bomberman.getY();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                if (contentFileLever1[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    walls.add(object);
                } else if (contentFileLever1[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    // addBombItem : (2,7)
                    if ((i == 2 && j == 7) || (i == 1 && j == 10) || (i == 7 && j == 14) || (i == 11 && j == 19)) {
                        ((Brick) object).setHasItemAddBomb(true);
                    } else if ((i == 2 && j == 13) || (i == 2 && j == 23) || (i == 11 && j == 16)) {
                        ((Brick) object).setHasFlameItem(true);
                    } else if ((i == 7 && j == 1) || (i == 7 && j == 4) || (i == 1 && j == 7)) {
                        ((Brick) object).setHasSpeedItem(true);
                    } else if (i == 5 && j == 14) {
                        if (checkToPlacePortal) {
                            ((Brick) object).setHasPortal(true);
                        }
                    }
                    bricks.add(object);
                } else if (contentFileLever1[i][j] == '1') {
                    object = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
                    ballooms.add(object);
                } else if (contentFileLever1[i][j] == '2') {
                    object = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                    oneals.add(object);
                } else if (contentFileLever1[i][j] == '3') {
                    object = new Doll(j, i, Sprite.doll_right1.getFxImage());
                    dolls.add(object);
                } else if (contentFileLever1[i][j] == '4') {
                    object = new Kondoria(j, i, Sprite.kondoria_right1.getFxImage());
                    kondorias.add(object);
                }


            }
        }
        //showInfoPanel();
        //Sound.play(Sound.BACKGROUND, 100);
        //setSound();
        InfoPanel.updateInfo();

    }


    public void update() {
        bomberman.update();
        walls.forEach(Entity::update);
        bricks.forEach(Entity::update);
        bomb_items.forEach(Entity::update);

        ballooms.forEach(Entity::update);
        oneals.forEach(Entity::update);
        dolls.forEach(Entity::update);
        kondorias.forEach(Entity::update);

        addBomb_items.forEach(Entity::update);
        flame_items.forEach(Entity::update);
        speed_items.forEach(Entity::update);
        if (portal != null) {
            portal.update();
        }

        for (Entity e : bricks) {
            if (((Brick) e).isDestroyed() && ((Brick) e).isHasItemAddBomb() && ((Brick) e).isCheckItemApear()) {
                addBomb_items.add(new AddBombItem(e.getXUnit(), e.getYUnit(), Sprite.powerup_bombs.getFxImage()));
            } else if (((Brick) e).isDestroyed() && ((Brick) e).isHasFlameItem() && ((Brick) e).isCheckItemApear()) {
                flame_items.add(new FlameItem(e.getXUnit(), e.getYUnit(), Sprite.powerup_flames.getFxImage()));
            } else if (((Brick) e).isDestroyed() && ((Brick) e).isHasSpeedItem() && ((Brick) e).isCheckItemApear()) {
                speed_items.add(new SpeedItem(e.getXUnit(), e.getYUnit(), Sprite.powerup_speed.getFxImage()));
            } else if (((Brick) e).isDestroyed() && ((Brick) e).isHasPortal() && ((Brick) e).isCheckItemApear()) {
                portal = new Portal(e.getXUnit(), e.getYUnit(), Sprite.portal.getFxImage());
            }
        }


        removeEntityDisappeared();
    }

    public void renderEntities() {
        //gc.clearRect(bomberman.getX()-2, bomberman.getY()-3, 32, 39);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        InfoPanel.showInfoPanel(gc);
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
        speed_items.forEach(g -> g.render(gc));
        dolls.forEach(g -> g.render(gc));
        kondorias.forEach(g -> g.render(gc));
        if (portal != null) {
            portal.render(gc);
        }
//        flame_items.forEach(g -> g.render(gc));
//        speed_items.forEach(g -> g.render(gc));
        //bomb_items.forEach(g -> g.render(gc));

        for (Entity e : bomb_items) {
            if (((BombItem) e).explored) {
                ((BombItem) e).frameRender(gc);
            } else {
                e.render(gc);
            }
        }


    }

    public void renderStillObject() {
        //walls.forEach(g -> g.render(gc));
    }

    public void removeEntityDisappeared() {
        for (Entity e : bomb_items) {
            if (e.getImg() == null) {
                if(BombItem.checkTimeToSetNull-- <=0) {
                    bomb_items.remove(e);
                }
                break;

            }
        }

        for (Entity e : ballooms) {
            if (e.getImg() == null) {
                ballooms.remove(e);
                break;

            }
        }

        for (Entity e : oneals) {
            if (e.getImg() == null) {
                oneals.remove(e);
                break;
            }
        }

        for (Entity e : dolls) {
            if (e.getImg() == null) {
                dolls.remove(e);
                break;
            }
        }

        for (Entity e : kondorias) {
            if (e.getImg() == null) {
                kondorias.remove(e);
                break;
            }
        }

        if (bomberman.getImg() == null) {

        }

        for (Entity e : bricks) {
            if (e.getImg() == null) {
                bricks.remove(e);
                break;
            }
        }

        for (Entity e : addBomb_items) {
            if (e.getImg() == null) {
                addBomb_items.remove(e);
                break;
            }
        }
        if (portal != null) {
            if (portal.getImg() == null) {
                portal = null;
                checkToNext = true;
            }
        }

    }

    public static void resetForNewLevel() {
        ballooms.clear();
        oneals.clear();
        kondorias.clear();
        dolls.clear();
        walls.clear();
        bricks.clear();
        addBomb_items.clear();
        flame_items.clear();
        speed_items.clear();
        bomb_items.clear();
        portal = null;

        BombItem.setBombQty(1);
        BombItem.setFlameLen(1);
        Bomber.setVelocity(2);
        Bomber.bombCountWillBePlaced = 0;

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        positionX = bomberman.getX();
        positionY = bomberman.getY();
    }

    static Media sound;

    @SuppressWarnings("deprecation")
    public static void setSound() {

//        try {
//            sound = new Media(new File("res/textures/sound.mp3").toURL().toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
        URL url = BombermanGame.class.getResource("res/textures/sound.mp3");
        Media media = new Media(String.valueOf(url));
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
    }



}
