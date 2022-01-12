package com.no4nick.nopg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class NopgApplication extends Application {

    private void startEventHandlers(Scene scene){
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()){
                case S -> {
                    Platform.runLater(() -> {
                        if (!LeftNewPaddle.isMovingDown()) {
                            LeftNewPaddle lp = new LeftNewPaddle(-1);
                            try {
                                lp.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            System.out.println(LeftNewPaddle.leftPaddle.getId());
                        }
                    });
                }
                case W -> {
                    Platform.runLater(() -> {
                        if (!LeftNewPaddle.isMovingUp()) {
                            LeftNewPaddle lp = new LeftNewPaddle(1);
                            try {
                                lp.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //                        System.out.println(RightPaddle.rightPaddle.getId());
                        }
                    });
                }
                case DOWN -> {
                    Platform.runLater(() -> {
                        if (!RightPaddle.isMovingDown()) {
                            RightPaddle rp = new RightPaddle(-1);
                            rp.start();
//                            System.out.println(RightPaddle.rightPaddle.getId());
                        }
                    });
                }
                case UP -> {
                    Platform.runLater(() -> {
                        if (!RightPaddle.isMovingUp()) {
                            RightPaddle rp = new RightPaddle(1);
                            rp.start();
//                        System.out.println(RightPaddle.rightPaddle.getId());
                        }
                    });
                }
                default -> {
                    System.out.println(key.getCode());
                }
            }
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            switch (key.getCode()){
                case W, S -> {
                    LeftNewPaddle.stopMove();
//                    System.out.println(LeftNewPaddle.leftPaddle.getId() + " Released");
                }
                case DOWN, UP -> {
                    RightPaddle.stopMove();
//                    System.out.println(RightPaddle.rightPaddle.getId() + " Released");
                }
                default -> {
//                    System.out.println(key.getCode());
                }
            }
        });
    }

    @Override
    public void start(Stage stage) throws IOException {
        new TickCounter(50).start();
        FXMLLoader fxmlLoader = new FXMLLoader(NopgApplication.class.getResource("Nopg_window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        //RightPaddle.setScene(scene);
        stage.setTitle("Nopg");
        stage.setScene(scene);
        startEventHandlers(scene);
        stage.show();
        new TickGUI().start();
    }

    public static void main(String[] args) {
        launch();
    }
}