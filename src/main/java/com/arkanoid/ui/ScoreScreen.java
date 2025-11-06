package com.arkanoid.ui;

import com.arkanoid.game.GameMain;
import com.arkanoid.game.GameStateManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScoreScreen  {

    static final int WIDTH = 750;
    static final int HEIGHT = 800;
    private int score;
    private GameMain gameMain;
    private Stage primaryStage;
    private static final String FONT_PATH = "/fonts/GameFont.TTF";

    public ScoreScreen() {

    }

    public ScoreScreen(Stage primaryStage, int score, GameMain gameMain) {
        this.primaryStage = primaryStage;
        this.score = score;
        this.gameMain = gameMain;
    }

    public void show() {

        String endMessage = (gameMain.getLevelComplete()) ? "LEVEL COMPLETE!" : "GAME OVER!";
        Label endTitle = new Label(endMessage);
        Font endTitleFont = Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH),
                40);
        endTitle.setFont(endTitleFont);
        endTitle.setTextFill(Color.WHITESMOKE);

        Label scoreLabel = new Label("SCORE: " + score);
        Font scoreLabelFont = Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH),
                40
        );
        scoreLabel.setFont(scoreLabelFont);
        scoreLabel.setTextFill(Color.WHITESMOKE);

        GameButton retryBtn = new GameButton("PLAY AGAIN");
        GameButton mainMenuBtn = new GameButton("MAIN MENU");
        GameButton exitBtn = new GameButton("EXIT");

        for (Button b : new Button[]{retryBtn, mainMenuBtn, exitBtn}) {
            ButtonEffects.applyHoverEffect(b);
        }

        VBox root = new VBox(20, endTitle, scoreLabel, retryBtn, mainMenuBtn, exitBtn);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(WIDTH, HEIGHT);

        Pane overPane = new Pane(root);

        //Làm mờ nền
        overPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        gameMain.getGamePane().getChildren().add(overPane);

        // Set Font
        retryBtn.setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36
        ));

        mainMenuBtn.setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36
        ));

        exitBtn.setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36
        ));
        // Nút chơi lại
        retryBtn.setOnAction(e -> {
            SoundEffect.playButtonClick();
            gameMain.getGamePane().getChildren().clear(); // Xóa toàn bộ
            gameMain.setPaused(false);
            gameMain.resetGame(); // Khởi tạo lại
        });

        // Nút quay về menu chính
        mainMenuBtn.setOnAction(e -> {
            SoundEffect.playButtonClick();
            try {
                GameMenu gameMenu = new GameMenu(gameMain.getBackgroundTexture(),
                                                    gameMain.getBallTexture(), gameMain.getPaddleTexture());
                gameMain.getGamePane().getChildren().clear();
                gameMain.getListBalls().clear();
                gameMain.getLaserBeams().clear();
                gameMain.getPowerUps().clear();
                GameStateManager.getInstance().resetScore();
                GameStateManager.getInstance().resetLives();
                gameMain.setPaused(false);
                gameMenu.start(primaryStage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Nút thoát
        exitBtn.setOnAction(e -> {
            SoundEffect.playButtonClick();
            primaryStage.close();
        });
    }
}