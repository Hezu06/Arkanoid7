package com.arkanoid.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class OptionsScreen {

    private static final String FONT_PATH = "/fonts/GameFont.TTF";

    public static void show(StackPane contentLayer, VBox menuBox, ImageView background,
                            ImageView ballImage, ImageView paddleImage, Slider volumeSlider) {
    // Step 1
        GameButton btnSetBackground = new GameButton("BACKGROUND");
        GameButton btnSetBall = new GameButton("BALL");
        GameButton btnSetPaddle = new GameButton("PADDLE");
        GameButton btnHighScore = new GameButton("HIGH SCORE");
        GameButton btnBack = new GameButton("BACK");

    //  SetFont
        for (Button b : new Button[]{btnSetBackground, btnSetBall, btnSetPaddle, btnHighScore, btnBack}) {
            ButtonEffects.applyHoverEffect(b);
            b.setFont(Font.loadFont(
                    OptionsScreen.class.getResourceAsStream(FONT_PATH), 36
            ));
        }

    //Step 2: See Background, Ball, Paddle, High score
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
        GameButton btnBackOfHighScore = new GameButton("BACK");

        Label volumeText = new Label("VOLUME");
        volumeText.setFont(Font.loadFont(OptionsScreen.class.getResourceAsStream(FONT_PATH), 36));
        volumeText.setTextFill(Color.BLACK);

        VBox volumeSliderBox = getVBox(volumeSlider, volumeText);

        VBox optionsBox = new VBox(30,
                btnSetBackground,
                btnSetBall,
                btnSetPaddle,
                volumeSliderBox,
                btnHighScore,
                btnBack);
        optionsBox.setAlignment(Pos.CENTER);
        FadeSmooth.smoothContent(contentLayer, optionsBox);

        btnSetBackground.setOnAction(e -> {
            SoundEffect.playButtonClick();
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
                SoundEffect.playButtonClick();
                background.setImage(new Image("assets/Background/galaxyBackground.jpg"));
            });

            btnBackground2.setOnAction(eB2 -> {
                SoundEffect.playButtonClick();
                background.setImage(new Image("assets/Background/blackBackground.jpg"));
            });

            btnBackground3.setOnAction(eB3 -> {
                SoundEffect.playButtonClick();
                background.setImage(new Image("assets/Background/beachBackground.jpg"));
            });

            btnBackground4.setOnAction(eB4 -> {
                SoundEffect.playButtonClick();
                background.setImage(new Image("assets/Background/grassBackground.png"));
            });

            btnBackOfBackground.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                FadeSmooth.smoothContent(contentLayer, optionsBox);
            });
        });

        btnSetBall.setOnAction(e -> {
            SoundEffect.playButtonClick();
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
                SoundEffect.playButtonClick();
                ballImage.setImage(new Image("assets/Ball/defaultBall.png"));
                previewBall.setImage(new Image("assets/Ball/defaultBall.png"));
            });

            btnBasketball.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                ballImage.setImage(new Image("assets/Ball/basketball.png"));
                previewBall.setImage(new Image("assets/Ball/basketball.png"));
            });

            btnVolleyball.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                ballImage.setImage(new Image("assets/Ball/volleyball.png"));
                previewBall.setImage(new Image("assets/Ball/volleyball.png"));
            });

            btnBackOfBall.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                FadeSmooth.smoothContent(contentLayer, optionsBox);
            });
        });

        btnSetPaddle.setOnAction(e -> {
            SoundEffect.playButtonClick();
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
                SoundEffect.playButtonClick();
                paddleImage.setImage(new Image("assets/Paddle/defaultPaddle.png"));
                previewPaddle.setImage(new Image("assets/Paddle/defaultPaddle.png"));
            });

            btnGrassPaddle.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                paddleImage.setImage(new Image("assets/Paddle/grassPaddle.png"));
                previewPaddle.setImage(new Image("assets/Paddle/grassPaddle.png"));
            });

            btnSandPaddle.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                paddleImage.setImage(new Image("assets/Paddle/sandPaddle.png"));
                previewPaddle.setImage(new Image("assets/Paddle/sandPaddle.png"));
            });

            btnBackOfPaddle.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                FadeSmooth.smoothContent(contentLayer, optionsBox);
            });
        });

        btnHighScore.setOnAction(e -> {
            SoundEffect.playButtonClick();

            btnBackOfHighScore.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                FadeSmooth.smoothContent(contentLayer, optionsBox);
            });

            Label title = new Label("LEADER BOARD");
            title.setFont(Font.loadFont(OptionsScreen.class.getResourceAsStream(FONT_PATH), 60));
            title.setTextFill(Color.ANTIQUEWHITE);

            // Fetch real scores
            List<Integer> topScores = HighScoreManager.getInstance().getTopScores();

            // Create the score list container
            VBox scoreList = new VBox(10);
            scoreList.setAlignment(Pos.CENTER);

            // Populate the score list with ranked labels
            if (topScores.isEmpty()) {
                Label emptyLabel = new Label("No scores recorded!");
                emptyLabel.setFont(Font.loadFont(OptionsScreen.class.getResourceAsStream(FONT_PATH), 36));
                emptyLabel.setTextFill(Color.GRAY);
                scoreList.getChildren().add(emptyLabel);
            } else {
                Font scoreFont = Font.loadFont(OptionsScreen.class.getResourceAsStream(FONT_PATH), 36);
                for (int i = 0; i < topScores.size(); i++) {
                    int rank = i + 1;
                    int scoreValue = topScores.get(i);

                    // Format the text: "Rank. Score"
                    Label scoreLabel = new Label(String.format("%d. %,d", rank, scoreValue));
                    scoreLabel.setFont(scoreFont);
                    if (i == 0) {
                        scoreLabel.setTextFill(Color.GOLD);
                        scoreList.getChildren().add(scoreLabel);
                        continue;
                    }
                    if (i == 1) {
                        scoreLabel.setTextFill(Color.SILVER);
                        scoreList.getChildren().add(scoreLabel);
                        continue;
                    }
                    if (i == 2) {
                        scoreLabel.setTextFill(Color.BROWN);
                        scoreList.getChildren().add(scoreLabel);
                        continue;
                    }
                    scoreLabel.setTextFill(Color.WHITE);
                    scoreList.getChildren().add(scoreLabel);
                }
            }

            VBox backgroundPlate = new VBox();
            backgroundPlate.setMaxWidth(300);
            backgroundPlate.setMaxHeight(VBox.USE_COMPUTED_SIZE);
            backgroundPlate.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.8);" + // Semi-transparent black (75% opacity)
                            "-fx-border-color: #FFFFF0;" +                 // Ivory border
                            "-fx-border-width: 5px;" +
                            "-fx-border-radius: 15px;" +
                            "-fx-background-radius: 15px;"
            );

            StackPane scoreContainer = new StackPane();
            scoreContainer.getChildren().addAll(backgroundPlate, scoreList);

            // Combine everything into the High Score Box
            VBox highScoreBox = new VBox(30, title, scoreContainer, btnBackOfHighScore);
            highScoreBox.setAlignment(Pos.CENTER);

            FadeSmooth.smoothContent(contentLayer, highScoreBox);
        });

        // Back button
        btnBack.setOnAction(e2 -> {
            SoundEffect.playButtonClick();
            FadeSmooth.smoothContent(contentLayer, menuBox);
        });
    }



    private static VBox getVBox(Slider volumeSlider, Label volumeText) {
        VBox volumeSliderBox = new VBox(10, volumeText, volumeSlider);

        final String styleNormal =
                "-fx-background-color: rgba(255, 255, 255, 0.6);" + // 80% trắng
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 20px;" +
                        "-fx-max-width: 250px;";

        final String styleHover =
                "-fx-background-color: rgba(255, 255, 255, 1.0);" + // 100% trắng (sáng lên)
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 20px;" +
                        "-fx-max-width: 250px;";

        volumeSliderBox.setStyle(styleNormal);

        // 3. Thêm sự kiện khi di chuột VÀO
        volumeSliderBox.setOnMouseEntered(e -> {
            volumeSliderBox.setStyle(styleHover);
        });

        // 4. Thêm sự kiện khi di chuột RA
        volumeSliderBox.setOnMouseExited(e -> {
            volumeSliderBox.setStyle(styleNormal);
        });

        volumeSliderBox.setAlignment(Pos.CENTER);
        return volumeSliderBox;
    }
}