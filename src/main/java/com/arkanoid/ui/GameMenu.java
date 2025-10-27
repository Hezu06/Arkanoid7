package com.arkanoid.ui;

import com.arkanoid.game.GameMain;
import com.arkanoid.level.Level;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class GameMenu extends Application {
    static final int Width = 750;
    static final int Height = 800;
    static final int widthBackground = 850;
    static final int heightBackground = 950;
    public static String pathBackground = "assets/Background/galaxyBackground.jpg";
    // Animation background
    public static void Transition(ImageView background) {
        background.setFitWidth(widthBackground);
        background.setFitHeight(heightBackground);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(4), background);
        tt.setFromX(0);
        tt.setToX(-40);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();
    }

    @Override
    public void start(Stage primaryStage) {
        // Root
        StackPane root = new StackPane();

        // Background
        Image image = new Image(pathBackground);
        ImageView background = new ImageView(image);
        Transition(background);

        Image ball = new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("/assets/Ball/defaultBall.png")));
        ImageView ballImage = new ImageView(ball);


        // Layer chứa menu thay đổi
        StackPane contentLayer = new StackPane();

        root.getChildren().addAll(background, contentLayer);

        Scene scene = new Scene(root, Width, Height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();

        // ====================== MENU CHÍNH ======================
        Label title = new Label("BRICK BREAKER");
        Font titleFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"),
                50 // cỡ chữ
        );
        title.setFont(titleFont);
        title.setTextFill(Color.WHITESMOKE);

        GameButton btnPlay = new GameButton("PLAY");
        GameButton btnOptions = new GameButton("OPTIONS");
        GameButton btnExit = new GameButton("EXIT");

        Font buttonFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"),
                30
        );
        for (Button b : new Button[]{btnPlay, btnOptions, btnExit}) {
            ButtonEffects.applyHoverEffect(b);
            b.setFont(buttonFont);
        }

        VBox menuBox = new VBox(30, title, btnPlay, btnOptions, btnExit);
        menuBox.setAlignment(Pos.CENTER);
        fadeSmooth.smoothContent(contentLayer, menuBox);

        // ====================== NÚT PLAY ======================
        btnPlay.setOnAction(e -> {
            Label startLabel = new Label("CHOOSE");
            startLabel.setFont(titleFont);
            startLabel.setTextFill(Color.WHITESMOKE);

            GameButton btnHard = new GameButton("HARD");
            GameButton btnVeryHard = new GameButton("VERY HARD");
            GameButton btnAsian = new GameButton("ASIAN");
            GameButton btnBack = new GameButton("BACK");

            for (Button b : new Button[]{btnHard, btnVeryHard, btnAsian, btnBack}) {
                ButtonEffects.applyHoverEffect(b);
                b.setFont(buttonFont);
            }

            VBox playBox = new VBox(30, startLabel, btnHard, btnVeryHard, btnAsian, btnBack);
            playBox.setAlignment(Pos.CENTER);

            fadeSmooth.smoothContent(contentLayer, playBox);

            btnBack.setOnAction(e1 -> fadeSmooth.smoothContent(contentLayer, menuBox));
            btnAsian.setOnAction(e2 -> {
                GameMain gameMain = new GameMain();
                gameMain.setLevelDifficulty(Level.LevelDifficulty.ASIAN);
                gameMain.setBallTexture(ballImage);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            btnVeryHard.setOnAction(e3 -> {
                GameMain gameMain = new GameMain();
                gameMain.setBallTexture(ballImage);
                gameMain.setLevelDifficulty(Level.LevelDifficulty.VERY_HARD);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            btnHard.setOnAction(e4 -> {
                GameMain gameMain = new GameMain();
                gameMain.setBallTexture(ballImage);
                gameMain.setLevelDifficulty(Level.LevelDifficulty.HARD);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        // ====================== NÚT OPTIONS ======================
        btnOptions.setOnAction(e2 -> {
            optionsScreen.show(contentLayer, menuBox, background, ballImage);
        });

        // ====================== NÚT EXIT ======================
        btnExit.setOnAction(e3 -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
}