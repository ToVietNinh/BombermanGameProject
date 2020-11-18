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
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> walls = new ArrayList<>();
    public static List<Entity> bricks = new ArrayList<>();
    public static List<Entity> ballooms = new ArrayList<>();
    public static Entity bomberman;
    public static boolean goUp, goDown, goLeft, goRight;
    public static int positionX, positionY;
    public char [][] contentFileLever1 = new char[14][32];


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
        scene.setFill(Color.rgb(80,160,0));

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()){
                            case UP: goUp = true;break;
                            case DOWN: goDown = true;break;
                            case LEFT: goLeft = true;break;
                            case RIGHT: goRight = true;break;
                        }
                    }
                }
        );
        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()){
                            case UP: goUp = false;break;
                            case DOWN: goDown = false;break;
                            case LEFT: goLeft = false;break;
                            case RIGHT: goRight = false;break;
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
    }

    //FileInputStream fileInputStream = new FileInputStream("mapLevel1.txt");
    public void createMap() throws Exception{
        FileReader fr = new FileReader("C:/Users/Admin/Desktop/bomberman_project/res/levels/mapLevel1.txt");
        BufferedReader br = new BufferedReader(fr);
        for(int i=0; i<HEIGHT; i++) {
            String line = br.readLine();
            for(int j=0; j< WIDTH; j++){
                contentFileLever1[i][j] = line.charAt(j);

            }
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                if(contentFileLever1[i][j] == '#'){
                    object = new Wall(j,i,Sprite.wall.getFxImage());
                    walls.add(object);
                }
                else if(contentFileLever1[i][j] == '*'){
                    object = new Brick(j,i,Sprite.brick.getFxImage());
                    bricks.add(object);
                }

            }
        }
    }



    public void update(){
        walls.forEach(Entity::update);
        bomberman.update();
    }

    public void renderEntities() {
        gc.clearRect(bomberman.getX()-2, bomberman.getY()-3, 32, 39);
        bomberman.render(gc);
        bricks.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
    }

    public void renderStillObject(){
        //walls.forEach(g -> g.render(gc));
    }
}
