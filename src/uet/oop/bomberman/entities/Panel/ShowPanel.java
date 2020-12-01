package uet.oop.bomberman.entities.Panel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Keyboard;
import uet.oop.bomberman.entities.Item.Portal;

import java.io.FileInputStream;
public class ShowPanel {

    public static void renderTransferLevelScreen(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 992, 448);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 60));
        gc.fillText("Level: " + Portal.nowLevel+1, 400, 250);
    }

    public static void renderGameOverScreen(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 992, 448);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 60));
        gc.fillText("You Lose!\nGame Over 🙁", 350, 200);
        gc.fillText("Your score: " + InfoPanel.nowScore , 350, 350);
    }

    public static void renderVictoryScreen(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 992, 448);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 60));
        gc.fillText("You win!\nCongrats!! 🙂", 350, 200);
        gc.fillText("Your score: " , 350, 350);
    }
}
