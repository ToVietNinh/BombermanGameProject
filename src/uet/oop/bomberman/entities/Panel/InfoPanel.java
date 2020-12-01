package uet.oop.bomberman.entities.Panel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.oop.bomberman.entities.Bomber;

import java.util.Timer;
import java.util.TimerTask;

public class InfoPanel {
    protected static int timeRemain = 150;
    protected static int nowLevel = 1;
    protected static int liveRemain = 3;
    protected static int nowScore = 0;
    protected static int delay = 1500;
    protected static int period = 1000;
    public static void showInfoPanel(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,416,992,448);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 15));
        gc.fillText("Time: " + timeRemain, 0,440);
        gc.fillText("Level: " + nowLevel, 200,440);
        gc.fillText("Live: " +  Bomber.numLiver, 400, 440);
        gc.fillText("Score: " + nowScore, 600, 440);
    }

    public static void updateInfo() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                delay += 400;
                period += 400;
                timeRemain--;
                if(timeRemain<=1) timer.cancel();
            }
        },delay, period);
        timeRemain = 250;
    }
}
