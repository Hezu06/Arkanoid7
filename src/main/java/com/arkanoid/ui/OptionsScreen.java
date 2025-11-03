package com.arkanoid.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class OptionsScreen {

    private static final String FONT_PATH = "/fonts/GameFont.TTF";

    public static void show(StackPane contentLayer, VBox menuBox, ImageView background,
                            ImageView ballImage, ImageView paddleImage) {
    // Step 1
        GameButton btnSetBackground = new GameButton("BACKGROUND");
        GameButton btnSetBall = new GameButton("BALL");
        GameButton btnSetPaddle = new GameButton("PADDLE");
        GameButton btnBack = new GameButton("BACK");

    //  SetFont
        for (Button b : new Button[]{btnSetBackground, btnSetBall, btnSetPaddle, btnBack}) {
            ButtonEffects.applyHoverEffect(b);
            b.setFont(Font.loadFont(
                    OptionsScreen.class.getResourceAsStream(FONT_PATH), 36
            ));
        }

    //Step 2: Set Background
        GameButton btnBackground1 = new GameButton("GALAXY");
        GameButton btnBackground2 = new GameButton("BLACK");
        GameButton btnBackground3 = new GameButton("BEACH");
        GameButton btnBackground4 = new GameButton("GRASS");

        GameButton btnDefaultBall = new GameButton("DEFAULT");
        GameButton btnBasketball = new GameButton("BASKETBALL");
        GameButton btnVolleyball = new GameButton("VOLLEYBALL");

        GameButton btnDefaultPaddle = new GameButton("DEFAULT");
        GameButton btnGrassPaddle = new GameButton("GRASS");
        GameButton btnSandPaddle = new GameButton("SAND");

        GameButton btnBackOfBackground = new GameButton("BACK");
        GameButton btnBackOfBall = new GameButton("BACK");
        GameButton btnBackOfPaddle = new GameButton("BACK");



        VBox optionsBox = new VBox(30, btnSetBackground, btnSetBall, btnSetPaddle, btnBack);
        optionsBox.setAlignment(Pos.CENTER);
        FadeSmooth.smoothContent(contentLayer, optionsBox);

        btnSetBackground.setOnAction(e -> {
            //SetFont
            for (Button b : new Button[]{btnBackground1, btnBackground2, btnBackground3, btnBackground4, btnBackOfBackground}) {
                ButtonEffects.applyHoverEffect(b);
                b.setFont(Font.loadFont(
                        OptionsScreen.class.getResourceAsStream(FONT_PATH), 36
                ));
            }

            VBox backgroundsBox = new VBox(30, btnBackground1, btnBackground2, btnBackground3, btnBackground4, btnBackOfBackground);
            backgroundsBox.setAlignment(Pos.CENTER);
            FadeSmooth.smoothContent(contentLayer, backgroundsBox);

            btnBackground1.setOnAction(eB1 -> {
                background.setImage(new Image("assets/Background/galaxyBackground.jpg"));
            });

            btnBackground2.setOnAction(eB2 -> {
                background.setImage(new Image("assets/Background/blackBackground.jpg"));
            });

            btnBackground3.setOnAction(eB3 -> {
                background.setImage(new Image("assets/Background/beachBackground.jpg"));
            });

            btnBackground4.setOnAction(eB4 -> {
                background.setImage(new Image("assets/Background/grassBackground.png"));
            });

            btnBackOfBackground.setOnAction(e1 -> FadeSmooth.smoothContent(contentLayer, optionsBox));
        });

        btnSetBall.setOnAction(e -> {
            //SetFont
            for (Button b : new Button[]{btnDefaultBall, btnBasketball, btnVolleyball, btnBackOfBall}) {
                ButtonEffects.applyHoverEffect(b);
                b.setFont(Font.loadFont(
                        OptionsScreen.class.getResourceAsStream(FONT_PATH), 36
                ));
            }

            ImageView previewBall = new ImageView(ballImage.getImage());
            previewBall.setFitWidth(64);
            previewBall.setFitHeight(64);

            VBox ballsBox = new VBox(30, previewBall,btnDefaultBall, btnBasketball, btnVolleyball, btnBackOfBall);
            ballsBox.setAlignment(Pos.CENTER);
            FadeSmooth.smoothContent(contentLayer, ballsBox);

            btnDefaultBall.setOnAction(e1 -> {
                ballImage.setImage(new Image("assets/Ball/defaultBall.png"));
                previewBall.setImage(new Image("assets/Ball/defaultBall.png"));
            });

            btnBasketball.setOnAction(e1 -> {
                ballImage.setImage(new Image("assets/Ball/basketball.png"));
                previewBall.setImage(new Image("assets/Ball/basketball.png"));
            });

            btnVolleyball.setOnAction(e1 -> {
                ballImage.setImage(new Image("assets/Ball/volleyball.png"));
                previewBall.setImage(new Image("assets/Ball/volleyball.png"));
            });

            btnBackOfBall.setOnAction(e1 -> {
                FadeSmooth.smoothContent(contentLayer, optionsBox);
            });
        });

        btnSetPaddle.setOnAction(e -> {
            //SetFont
            for (Button b : new Button[]{btnDefaultPaddle, btnGrassPaddle, btnSandPaddle, btnBackOfPaddle}) {
                ButtonEffects.applyHoverEffect(b);
                b.setFont(Font.loadFont(
                        OptionsScreen.class.getResourceAsStream(FONT_PATH), 36
                ));
            }

            ImageView previewPaddle = new ImageView(paddleImage.getImage());
            previewPaddle.setFitWidth(120);
            previewPaddle.setFitHeight(20);

            VBox ballsBox = new VBox(30, previewPaddle, btnDefaultPaddle, btnGrassPaddle, btnSandPaddle, btnBackOfPaddle);
            ballsBox.setAlignment(Pos.CENTER);
            FadeSmooth.smoothContent(contentLayer, ballsBox);

            btnDefaultPaddle.setOnAction(e1 -> {
                paddleImage.setImage(new Image("assets/Paddle/defaultPaddle.png"));
                previewPaddle.setImage(new Image("assets/Paddle/defaultPaddle.png"));
            });

            btnGrassPaddle.setOnAction(e1 -> {
                paddleImage.setImage(new Image("assets/Paddle/grassPaddle.png"));
                previewPaddle.setImage(new Image("assets/Paddle/grassPaddle.png"));
            });

            btnSandPaddle.setOnAction(e1 -> {
                paddleImage.setImage(new Image("assets/Paddle/sandPaddle.png"));
                previewPaddle.setImage(new Image("assets/Paddle/sandPaddle.png"));
            });

            btnBackOfPaddle.setOnAction(e1 -> {
                FadeSmooth.smoothContent(contentLayer, optionsBox);
            });
        });

        // Back button
        btnBack.setOnAction(e2 -> FadeSmooth.smoothContent(contentLayer, menuBox));
    }
}