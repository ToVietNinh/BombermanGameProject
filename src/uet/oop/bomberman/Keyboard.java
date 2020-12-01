package uet.oop.bomberman;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Keyboard {
    public boolean up, down, left, right, enter;
    public boolean release = false;// su dung de dieu huong lua chon trong menu game

    public Keyboard() {
        up = false;
        down = false;
        left = false;
        right = false;
        enter = false;
    }

    public void updateKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
            up = true;
        }
        if (e.getCode() == KeyCode.DOWN) {
            down = true;
        }
        if (e.getCode() == KeyCode.LEFT) {
            left = true;
        }
        if (e.getCode() == KeyCode.RIGHT) {
            right = true;
        }
        if (e.getCode() == KeyCode.ENTER) {
            enter = true;
        }
        release = false;
    }

    public void updateKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
            up = false;
        }
        if (e.getCode() == KeyCode.DOWN) {
            down = false;
        }
        if (e.getCode() == KeyCode.LEFT) {
            left = false;
        }
        if (e.getCode() == KeyCode.RIGHT) {
            right = false;
        }
        if (e.getCode() == KeyCode.ENTER) {
            enter = false;
        }
        release = true;
    }

    public void setRelease(boolean release) {
        this.release = release;
    }
}

