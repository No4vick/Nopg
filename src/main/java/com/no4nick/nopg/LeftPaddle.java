package com.no4nick.nopg;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LeftPaddle extends Thread {
    static Rectangle leftPaddle = new Rectangle();
    static double speed = 10.0;
    static boolean movingDown;
    static boolean movingUp;
    static int tick;

    public LeftPaddle(Rectangle newLeftPaddle){
        leftPaddle = newLeftPaddle;
        movingDown = false;
        movingUp = false;
        tick = 0;
    }

    public LeftPaddle(int direction){
        movingDown = false;
        movingUp = false;
        switch (direction){
            case 1 -> movingUp = true;
            case -1 -> movingDown = true;
        }
        tick = 0;
    }

    private static void Sleep(){
        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void reset(){
        leftPaddle.setY(0);
    }

    public void run(){
        if (ResetContainer.isReset()){
            reset();
            stopMove();
        }
        if (movingUp)
            moveUp();
        if (movingDown)
            moveDown();
    }

    public static synchronized boolean isMovingUp(){
        return movingUp;
    }

    public static synchronized boolean isMovingDown(){
        return movingDown;
    }

    public static synchronized void stopMove(){
        movingDown = false;
        movingUp = false;
    }

    public synchronized void movingDown(){
        if (ResetContainer.isReset()){
            reset();
            stopMove();
        }
        if (leftPaddle.getY() < 190) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> leftPaddle.setY(leftPaddle.getY() + speed)));
            timeline.setCycleCount(0);
            timeline.play();
        }
    }

    public synchronized void movingUp(){
        if (ResetContainer.isReset()){
            reset();
            stopMove();
        }
        if (leftPaddle.getY() > -190) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> leftPaddle.setY(leftPaddle.getY() - speed)));
            timeline.setCycleCount(0);
            timeline.play();
        }
    }

    public synchronized void moveDown(){
        while (movingDown) {
            movingDown();
            Sleep();
        }
    }
    public synchronized void moveUp(){
        while (movingUp) {
            movingUp();
            Sleep();
        }
    }
    public static synchronized boolean didBallReach(double ballX){
        return ballX <= (leftPaddle.getLayoutX() - 360);
    }

    public static synchronized boolean didBallReachWall(double ballX){
        return ballX <= (leftPaddle.getLayoutX() - 380);
    }

    public static synchronized boolean didBallHit(double ballY){
        return (ballY < (leftPaddle.getY() + 50) && ballY > (leftPaddle.getY() - 50));
    }

}

