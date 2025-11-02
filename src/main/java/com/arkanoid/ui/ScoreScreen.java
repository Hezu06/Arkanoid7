package com.arkanoid.ui;

import com.arkanoid.game.GameMain;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScoreScreen  {
    static final int WIDTH = 750;
    static final int HEIGHT = 800;
    private int score;
    private Stage stage;
    private GameMain gameMain;
    private Stage primaryStage;

    public ScoreScreen() {

    }

    public ScoreScreen(Stage primaryStage, int score, GameMain gameMain) {
        this.primaryStage = primaryStage;
        this.score = score;
        this.gameMain = gameMain;
    }

    public void show() {
        Label title = new Label("🎮 Game Over!");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: red; -fx-font-weight: bold;");

        Label scoreLabel = new Label("Your score: " + score);
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #222;");

        GameButton retryBtn = new GameButton("PLAY AGAIN");
        GameButton mainMenuBtn = new GameButton("MAIN MENU");
        GameButton exitBtn = new GameButton("EXIT");

        for (Button b : new Button[]{retryBtn, mainMenuBtn, exitBtn}) {
            ButtonEffects.applyHoverEffect(b);
        }

        VBox root = new VBox(20, title, scoreLabel, retryBtn, mainMenuBtn, exitBtn);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(WIDTH, HEIGHT);

        Pane overPane = new Pane(root);
        gameMain.getGamePane().getChildren().add(overPane);
        // Set Font
        retryBtn.setFont(Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"), 36
        ));

        mainMenuBtn.setFont(Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"), 36
        ));

        exitBtn.setFont(Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"), 36
        ));
        // Nút chơi lại
        retryBtn.setOnAction(e -> {
            gameMain.getGamePane().getChildren().clear(); // Xóa toàn bộ
            gameMain.setPaused(false);
            gameMain.resetGame(); // Khởi tạo lại
        });

        // Nút quay về menu chính
        mainMenuBtn.setOnAction(e -> {
            try {
                GameMenu gameMenu = new GameMenu(gameMain.getBackgroundTexture(),
                                                    gameMain.getBallTexture(), gameMain.getPaddleTexture());
                gameMain.getGamePane().getChildren().clear();
                gameMain.setPaused(false);
                gameMenu.start(primaryStage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Nút thoát
        exitBtn.setOnAction(e -> primaryStage.close());
    }
}