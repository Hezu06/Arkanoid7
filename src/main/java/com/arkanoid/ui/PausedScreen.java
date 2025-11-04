package com.arkanoid.ui;

import com.arkanoid.game.GameMain;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PausedScreen {
    static final int WIDTH = 750;
    static final int HEIGHT = 800;

    private Stage primaryStage;
    private int score;
    private GameMain gameMain;

    private static final String FONT_PATH = "/fonts/GameFont.TTF";

    public PausedScreen() {};
    public PausedScreen(Stage primaryStage, GameMain gameMain) {
        this.primaryStage = primaryStage;
        this.gameMain = gameMain;
    }

    public void show() {
        GameButton btnResume = new GameButton("RESUME");
        GameButton btnPlayAgain = new GameButton("PLAY AGAIN");
        GameButton btnMainMenu = new GameButton("MAIN MENU");
        GameButton btnExit = new GameButton("EXIT");

        for (Button b : new Button[]{btnResume, btnPlayAgain, btnMainMenu, btnExit}) {
            ButtonEffects.applyHoverEffect(b);
        }

        VBox pauseBox = new VBox(20, btnResume, btnPlayAgain, btnMainMenu, btnExit);
        pauseBox.setAlignment(Pos.CENTER);
        pauseBox.setPrefSize(WIDTH, HEIGHT);

        Pane panePause = new Pane(pauseBox);

        gameMain.getGamePane().getChildren().add(panePause);

        //Set font
        btnResume.setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36
        ));

        btnPlayAgain.setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36
        ));

        btnMainMenu.setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36
        ));

        btnExit.setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36
        ));

        //Set activities
        btnResume.setOnAction(e-> {
            gameMain.getGamePane().getChildren().remove(panePause);
            gameMain.setPaused(false);
        });

        btnPlayAgain.setOnAction(e1-> {
            gameMain.getGamePane().getChildren().clear(); // Xóa toàn bộ
            gameMain.setPaused(false);
            gameMain.resetGame(); // Khởi tạo lại
        });

        btnMainMenu.setOnAction(e2-> {
            try {
                GameMenu gameMenu = new GameMenu(gameMain.getBackgroundTexture(),
                        gameMain.getBallTexture(), gameMain.getPaddleTexture());
                gameMain.getGamePane().getChildren().clear();
                gameMain.getListBalls().clear();
                gameMain.getLaserBeams().clear();
                gameMain.getPowerUps().clear();
                gameMain.setPaused(false);
                gameMenu.start(primaryStage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnExit.setOnAction(e3-> {
            primaryStage.close();
        });
    }
}
