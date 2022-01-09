package com.no4nick.nopg;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ScoreSetter extends Thread {
    static int leftScore = 0;
    static int rightScore = 0;
    static Text leftText;
    static Text rightText;
    static Rectangle ball;

    public ScoreSetter(Rectangle newBall){
        ball = newBall;
    }

    public ScoreSetter(Text newLeftScoreText, Text newRightScoreText){
        leftText = newLeftScoreText;
        rightText = newRightScoreText;
    }

    private void setScores(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
            leftText.setText(String.valueOf(leftScore));
            rightText.setText(String.valueOf(rightScore));
        }));
        timeline.setCycleCount(0);
        timeline.play();
    }

    private void resetScore(){
        leftScore = 0;
        rightScore = 0;
        setScores();
    }

    public void run(){
        while (!ResetContainer.isReset()) {
            new Ball().Run();
            if (ResetContainer.isReset())
                break;
            if (Ball.scoredRight)
                leftScore++;
            if (Ball.scoredLeft)
                rightScore++;
            setScores();
        }
        resetScore();
        Ball.Reset();
        System.out.println("End");
    }
}
