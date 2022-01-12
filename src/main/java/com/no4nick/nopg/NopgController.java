package com.no4nick.nopg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class NopgController {
    @FXML
    public Rectangle rightPaddle;
    @FXML
    public Rectangle ball;
    @FXML
    public Rectangle leftPaddle;
    public Text ticksText;
    public Button buttonReset;
    public Text leftScore;
    public Text rightScore;

    public void initialize(){
        new RightPaddle(rightPaddle);
        new LeftNewPaddle(leftPaddle);
//        Platform.runLater(() -> {
//            new TickGUI(ticksText);
//        });
        Ball.ball = ball;
        Platform.runLater(() -> {
            new ScoreSetter(leftScore, rightScore).start();
        });
    }

    @FXML
    public void onResetButtonClick() {
        ResetContainer.switchReset();
        leftPaddle.setTranslateY(0);
        rightPaddle.setTranslateY(0);
        System.out.println(Thread.activeCount());
        ResetContainer.switchReset();
        Platform.runLater(() -> {
            new ScoreSetter(leftScore, rightScore).start();
        });
    }
}
