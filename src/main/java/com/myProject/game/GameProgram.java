package com.myProject.game;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameProgram extends Application {

    private Label computerLbl = new Label("Wygrane komputera");
    private Label playerLbl = new Label("Twoje wygrane");

    private Label status = new Label();
    private Image imageback = new Image("file:src/main/resources/table.png");

    private boolean computerTurn;
    private boolean playerTurn;

    @Override
    public void start(Stage primaryStage) {

        status.setTextFill(Color.web("#FFF"));
        status.setFont(new Font("Arial", 24));

        computerLbl.setTextFill(Color.web("#FFF"));
        computerLbl.setFont(new Font("Arial", 24));

        playerLbl.setTextFill(Color.web("#FFF"));
        playerLbl.setFont(new Font("Arial", 24));

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Button restart = new Button();
        restart.setText("Nowa rozgrywka");
        restart.setOnAction((e) -> {
            if(e.isConsumed()) {
                //gameSatrter(restartGame);
                System.out.println("Gramy od nowa");
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
