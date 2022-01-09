package com.no4nick.nopg;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TickGUI extends Thread {
    private static Text text;

    public TickGUI(Text newText){
        text = newText;
    }
    public TickGUI(){}
    public void run(){

        while (true){
//            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> text.setText(String.valueOf(TickCounter.Tick()))));
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> text.setText(String.valueOf(Thread.activeCount()))));
            timeline.setCycleCount(0);
            timeline.play();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
