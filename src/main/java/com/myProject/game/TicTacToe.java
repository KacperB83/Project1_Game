package com.myProject.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[5][5];
    private List<Combo> combos = new ArrayList<>();
    private Pane root = new Pane();

    private Parent createContent() {

        root.setPrefSize(750, 750);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 150);
                tile.setTranslateY(i* 150);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }
        //poziom
        for (int y = 0; y < 5; y++) {
            combos.add(new Combo(board[0][y], board[0][y], board[0][y], board[0][y], board[0][y]));
        }
        //pion
        for (int x = 0; x < 5; x++) {
            combos.add(new Combo(board[x][0], board[x][0], board[x][0], board[x][0], board[x][0]));
        }
        //przekątne
        combos.add(new Combo(board[0][0], board[1][1], board[2][2], board[3][3], board[4][4]));
        combos.add(new Combo(board[4][0], board[3][1], board[2][2], board[1][3], board[0][4]));
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                break;
            }
        }
    }
    private void playWinAnimation (Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());

        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.tiles[4].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[4].getCenterY())));
        timeline.play();
    }

    private class Combo {
        private Tile[] tiles;
        public Combo (Tile... tiles) {
            this.tiles = tiles;
        }
        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;
            return tiles[0].getValue().equals(tiles[1].getValue())
                    &&tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(150, 150);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(46));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (!playable) return;

                if(event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX) return;
                    drawX();
                    turnX = false;
                    checkState();
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX) return;
                    drawO();
                    turnX = true;
                    checkState();
                }
            });
        }
        public double getCenterX() {
            return getTranslateX() + 75;
        }
        public double getCenterY() {
            return getTranslateY() + 75;
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {
            text.setText("X");
        }

        private void drawO() {
            text.setText("O");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
