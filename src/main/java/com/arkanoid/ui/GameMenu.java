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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMenu extends Application {
    static final int Width = 750;
    static final int Height = 800;
    static final int widthBackground = 850;
    static final int heightBackground = 950;

    // Animation cho background
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

    // Hàm đổi nội dung với fade mượt
    private void setContent(StackPane contentLayer, VBox newContent) {
        if (!contentLayer.getChildren().isEmpty()) {
            VBox oldContent = (VBox) contentLayer.getChildren().get(0);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), oldContent);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> {
                contentLayer.getChildren().setAll(newContent);

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newContent);
                newContent.setOpacity(0.0);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        } else {
            contentLayer.getChildren().setAll(newContent);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Root
        StackPane root = new StackPane();

        // Background
        Image image = new Image("/assets/Background/galaxyBackground.jpg");
        ImageView background = new ImageView(image);
        Transition(background);

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
//        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setFont(titleFont);
        title.setTextFill(Color.WHITESMOKE);

        Button btnPlay = new Button("Play");
        Button btnOptions = new Button("Options");
        Button btnExit = new Button("Exit");

        Font buttonFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"),
                30
        );

        for (Button b : new Button[]{btnPlay, btnOptions, btnExit}) {
            b.setPrefSize(200, 50);
            b.setFont(buttonFont);
            b.setOpacity(0.6);
            ButtonEffects.applyHoverEffect(b);
        }

        VBox menuBox = new VBox(30, title, btnPlay, btnOptions, btnExit);
        menuBox.setAlignment(Pos.CENTER);
        setContent(contentLayer, menuBox);

        // ====================== NÚT PLAY ======================
        btnPlay.setOnAction(e -> {
            Label startLabel = new Label("CHOOSE");
            startLabel.setFont(titleFont);
            startLabel.setTextFill(Color.WHITESMOKE);

            Button btnAsian = new Button("Asian");
            Button btnVeryHard = new Button("Very Hard");
            Button btnHard = new Button("Hard");
            Button btnBack = new Button("Back");

            for (Button b : new Button[]{btnAsian, btnVeryHard, btnHard}) {
                b.setPrefSize(200, 50);
                b.setFont(buttonFont);
                b.setOpacity(0.6);
                ButtonEffects.applyHoverEffect(b);
            }

            btnBack.setPrefSize(100, 30);
            btnBack.setFont(buttonFont);
            btnBack.setOpacity(0.6);
            ButtonEffects.applyHoverEffect(btnBack);

            VBox playBox = new VBox(30, startLabel, btnAsian, btnVeryHard, btnHard, btnBack);
            playBox.setAlignment(Pos.CENTER);

            setContent(contentLayer, playBox);

            btnBack.setOnAction(e1 -> setContent(contentLayer, menuBox));
            btnAsian.setOnAction(e2 -> {
                GameMain gameMain = new GameMain();
                gameMain.setLevelDifficulty(Level.LevelDifficulty.ASIAN);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            btnVeryHard.setOnAction(e3 -> {
                GameMain gameMain = new GameMain();
                gameMain.setLevelDifficulty(Level.LevelDifficulty.VERY_HARD);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            btnHard.setOnAction(e4 -> {
                GameMain gameMain = new GameMain();
                gameMain.setLevelDifficulty(Level.LevelDifficulty.HARD);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        // ====================== NÚT OPTIONS ======================
        btnOptions.setOnAction(e -> {
            Label optionsLabel = new Label("OPTIONS");
            optionsLabel.setFont(titleFont);
            optionsLabel.setTextFill(Color.WHITESMOKE);

            Button btnBack = new Button("Back");
            btnBack.setPrefSize(100, 30);
            btnBack.setFont(buttonFont);
            btnBack.setOpacity(0.6);
            ButtonEffects.applyHoverEffect(btnBack);

            VBox optionsBox = new VBox(30, optionsLabel, btnBack);
            optionsBox.setAlignment(Pos.CENTER);

            setContent(contentLayer, optionsBox);

            btnBack.setOnAction(e2 -> setContent(contentLayer, menuBox));
        });

        // ====================== NÚT EXIT ======================
        btnExit.setOnAction(e -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
}