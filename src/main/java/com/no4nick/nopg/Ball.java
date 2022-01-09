package com.no4nick.nopg;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Ball extends Thread {
    static Rectangle ball;
    static int tick;
    static double speedX = 10.0;
    static double speedY = 0;
    static boolean movingRight = false;
    static boolean movingLeft = false;
    static boolean reset = false;
    static boolean scoredLeft = false;
    static boolean scoredRight = false;

    public Ball(Rectangle newBall){
        ball = newBall;
    }

    public Ball(){
    }

    private static void Sleep(){
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void switchDirection(){
        int modifier;
        if (speedY > 0)
            modifier = 1;
        else
            modifier = -1;
        speedY = new Random().nextFloat(0, 10) * modifier;
        movingLeft = !movingLeft;
        movingRight = !movingRight;
    }

    private static synchronized void boundsCheck(){
        if (ball.getTranslateY() < -225 || ball.getTranslateY() > 225)
            speedY = -speedY;
    }

    private synchronized void moveRight(){
//        System.out.println("ball moving right");
        if (TickCounter.didTickChange(tick)) {
            tick = TickCounter.Tick();
            boundsCheck();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
                ball.setTranslateX(ball.getTranslateX() + speedX);
                ball.setTranslateY(ball.getTranslateY() + speedY);
            }));
            timeline.setCycleCount(0);
            timeline.play();
        }

        if (RightPaddle.didBallReach(ball.getTranslateX())) {
            if (RightPaddle.didBallHit(ball.getTranslateY())) {
                System.out.println("ball switched direction");
                switchDirection();
            }
            else {
                if (RightPaddle.didBallReachWall(ball.getTranslateX())) {
                    scoredRight = true;
                    Reset();
                }
            }
        }
    }

    private static synchronized void moveLeft(){
        if (TickCounter.didTickChange(tick)) {
            tick = TickCounter.Tick();
            boundsCheck();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
                ball.setTranslateX(ball.getTranslateX() - speedX);
                ball.setTranslateY(ball.getTranslateY() + speedY);
            }));
            timeline.setCycleCount(0);
            timeline.play();
        }
        if (LeftNewPaddle.didBallReach(ball.getTranslateX())) {
            if (LeftNewPaddle.didBallHit(ball.getTranslateY())) {
                System.out.println("ball switched direction");
                switchDirection();
            }
            else {
                if (LeftNewPaddle.didBallReachWall(ball.getTranslateX())) {
                    scoredLeft = true;
                    Reset();
                }
            }
        }
    }

    public static synchronized void Reset(){
        ball.setTranslateY(0);
        ball.setTranslateX(0);
        speedY = 0;
        reset = true;
    }

    public void Run(){
        if (ResetContainer.isReset())
            return;
        scoredRight = false;
        scoredLeft = false;
        reset = false;
        Sleep();
        movingRight = new Random().nextBoolean();
        movingLeft = !movingRight;
        while(!reset && !ResetContainer.isReset()){
//            System.out.println(ball.getX());
            if (movingRight)
                moveRight();
            else
                moveLeft();
        }
        Reset();
    }
}
