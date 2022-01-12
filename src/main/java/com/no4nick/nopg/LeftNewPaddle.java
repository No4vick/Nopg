package com.no4nick.nopg;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static java.lang.Thread.sleep;

public class LeftNewPaddle extends Task {
    static Rectangle rightPaddle = new Rectangle();
    static double speed = 10.0;
    static boolean movingDown;
    static boolean movingUp;
    static int tick;

    public LeftNewPaddle(Rectangle newRightPaddle){
        rightPaddle = newRightPaddle;
        movingDown = false;
        movingUp = false;
        tick = 0;
    }


    public LeftNewPaddle(int direction){
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
        rightPaddle.setY(0);
    }

    public void run(){
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
        if (rightPaddle.getTranslateY() < 190 && TickCounter.didTickChange(tick)) {
            tick = TickCounter.Tick();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> rightPaddle.setTranslateY(rightPaddle.getTranslateY() + speed)));
            timeline.setCycleCount(0);
            timeline.play();
        }
    }

    public synchronized void movingUp(){
        if (ResetContainer.isReset()){
            reset();
            stopMove();
        }
        if (rightPaddle.getTranslateY() > -190 && TickCounter.didTickChange(tick)) {
            tick = TickCounter.Tick();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> rightPaddle.setTranslateY(rightPaddle.getTranslateY() - speed)));
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
//            System.out.println(rightPaddle.getY());
            Sleep();
        }
    }

    public static synchronized boolean didBallReach(double ballX){
        return ballX <= (rightPaddle.getLayoutX() - 360);
    }

    public static synchronized boolean didBallReachWall(double ballX){
        return ballX <= (rightPaddle.getLayoutX() - 380);
    }

    public static synchronized boolean didBallHit(double ballY){
        return (ballY < (rightPaddle.getTranslateY() + 50) && ballY > (rightPaddle.getTranslateY() - 50));
    }

    @Override
    protected Object call() throws Exception {
        run();
        return null;
    }
}
