package com.myProject.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    /*
       /* function alphaBeta(node, alpha, beta, maximisingPlayer) {
    var bestValue;
    if (node.children.length === 0) {
        bestValue = node.data;
    }
    else if (maximisingPlayer) {
        bestValue = alpha;

        // Recurse for all children of node.
        for (var i=0, c=node.children.length; i<c; i++) {
            var childValue = alphaBeta(node.children[i], bestValue, beta, false);
            bestValue = Math.max(bestValue, childValue);
            if (beta <= bestValue) {
                break;
            }
        }
    }
    else {
        bestValue = beta;

        // Recurse for all children of node.
        for (var i=0, c=node.children.length; i<c; i++) {
            var childValue = alphaBeta(node.children[i], alpha, bestValue, true);
            bestValue = Math.min(bestValue, childValue);
            if (bestValue <= alpha) {
                break;
            }
        }
    }
    return bestValue;
}*/

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[5][5];
    private List<Combo> combos = new ArrayList<>();
    private Pane root = new Pane();
    private Button button;

    private Parent createContent() {

        root.setPrefSize(900, 700);
        button = new Button();
        button.setText("Restart");
        button.setAlignment(Pos.TOP_CENTER);
       // button.setOnAction();
        root.getChildren().add(button);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 100);
                tile.setTranslateY(i* 100);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }
        //poziom
        for (int y = 0; y < 5; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y], board[3][y], board[4][y]));
        }
        //pion
        for (int x = 0; x < 5; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2], board[x][3], board[x][4]));
        }
        //przekątne
        combos.add(new Combo(board[0][0], board[1][1], board[2][2], board[3][3], board[4][4]));
        combos.add(new Combo(board[0][0], board[1][1], board[2][2], board[3][3], board[4][4]));

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TicTacToe");

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
        line.setEndX(combo.tiles[4].getCenterX());
        line.setEndY(combo.tiles[4].getCenterY());

        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
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
            if (tiles[0].getValue().isEmpty() || tiles[4].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    &&tiles[0].getValue().equals(tiles[2].getValue())
                    &&tiles[0].getValue().equals(tiles[3].getValue())
                    &&tiles[0].getValue().equals(tiles[4].getValue());
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(36));

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
            return getTranslateX() + 50;
        }
        public double getCenterY() {
            return getTranslateY() + 50;
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {text.setText("X");
        }

        private void drawO() {text.setText("O");}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
