package com.arkanoid.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScoreScreen {
    private int score;
    private Stage stage;

    public ScoreScreen(Stage stage, int score) {
        this.stage = stage;
        this.score = score;
    }

    public void show() {
        Label title = new Label("🎮 Game Over!");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: red; -fx-font-weight: bold;");

        Label scoreLabel = new Label("Your score: " + score);
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #222;");

        GameButton retryBtn = new GameButton("Play Again");
        GameButton mainMenuBtn = new GameButton("Main Menu");
        GameButton exitBtn = new GameButton("Exit");

        for (Button b : new Button[]{retryBtn, mainMenuBtn, exitBtn}) {
            ButtonEffects.applyHoverEffect(b);
        }

        // Nút chơi lại
        retryBtn.setOnAction(e -> {
            
        });

        // Nút quay về menu chính
        mainMenuBtn.setOnAction(e -> {

        });

        // Nút thoát

        exitBtn.setOnAction(e -> stage.close());

        VBox root = new VBox(20, title, scoreLabel, retryBtn, mainMenuBtn, exitBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffe, #ccc);");

        Scene scene = new Scene(root, 750, 950);
        stage.setScene(scene);
    }
}