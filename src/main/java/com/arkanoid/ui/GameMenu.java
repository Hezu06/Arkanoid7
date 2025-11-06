package com.arkanoid.ui;

import com.arkanoid.game.GameMain;
import com.arkanoid.level.Level;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    private static final int WIDTH = 750;
    private static final int HEIGHT = 800;
    static final int WIDTH_BACKGROUND = 850;
    static final int HEIGHT_BACKGROUND = 950;
    public static String pathBackground = "assets/Background/galaxyBackground.jpg";
    private static final String FONT_PATH = "/fonts/GameFont.TTF";

    // Animation background
    public static void Transition(ImageView background) {
        background.setFitWidth(WIDTH_BACKGROUND);
        background.setFitHeight(HEIGHT_BACKGROUND);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(4), background);
        tt.setFromX(0);
        tt.setToX(-40);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();
    }

    // Initialize Background
    private Image image = new Image(pathBackground);
    private ImageView background = new ImageView(image);

    // Ball and Paddle
    private Image ball = new Image(Objects.requireNonNull(getClass().
            getResourceAsStream("/assets/Ball/defaultBall.png")));
    private ImageView ballImage = new ImageView(ball);

    private Image paddle = new Image("assets/Paddle/defaultPaddle.png");
    private ImageView paddleImage = new ImageView(paddle);

    // Constructor GameMenu
    public GameMenu() {}
    public GameMenu(ImageView background, ImageView ballImage, ImageView paddleImage) {
        this.background = background;
        this.ballImage = ballImage;
        this.paddleImage = paddleImage;
    }

    private StackPane contentLayer;
    private VBox menuBox;

    // Getter
    public StackPane getContentLayer() {
        return contentLayer;
    }

    public VBox getMenuBox() {
        return menuBox;
    }

    @Override
    public void start(Stage primaryStage) {
        // Root
        StackPane root = new StackPane();

        Transition(background);

        // Layer chứa menu thay đổi
        contentLayer = new StackPane();

        root.getChildren().addAll(background, contentLayer);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);  // vô hiệu hóa thay đổi size;
        primaryStage.setTitle("BRICK BREAKER 36.0");
        Image stageIcon = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/assets/Ball/defaultBall.png")));
        primaryStage.getIcons().add(stageIcon);
        primaryStage.show();

        // ====================== NHẠC NỀN ======================
        SoundBackground.getInstance().playBackgroundMusic();

        Slider volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(50); // Đặt âm lượng mặc định là 50%
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(25);

        // Đặt chiều rộng mong muốn là 250 pixels
        volumeSlider.setMaxWidth(250);

        // ====================== CLICK SOUND =====================
        SoundEffect.load();

        // ====================== MENU CHÍNH ======================
        Label title = new Label("BRICK BREAKER");
        Font titleFont = Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH),
                80 // cỡ chữ
        );
        title.setFont(titleFont);
        title.setTextFill(Color.WHITESMOKE);

        GameButton btnPlay = new GameButton("PLAY");
        GameButton btnOptions = new GameButton("OPTIONS");
        GameButton btnExit = new GameButton("EXIT");

        Font buttonFont = Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH),
                30
        );
        for (Button b : new Button[]{btnPlay, btnOptions, btnExit}) {
            ButtonEffects.applyHoverEffect(b);
            b.setFont(buttonFont);
        }

        menuBox = new VBox(30, title, btnPlay, btnOptions, btnExit);
        menuBox.setAlignment(Pos.CENTER);
        FadeSmooth.smoothContent(contentLayer, menuBox);

        // ====================== NÚT PLAY ======================
        btnPlay.setOnAction(e -> {
            SoundEffect.playButtonClick();
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

            FadeSmooth.smoothContent(contentLayer, playBox);

            btnBack.setOnAction(e1 -> {
                SoundEffect.playButtonClick();
                FadeSmooth.smoothContent(contentLayer, menuBox);
            });

            btnAsian.setOnAction(e2 -> {
                SoundEffect.playButtonClick();
                GameMain gameMain = new GameMain(primaryStage);
                gameMain.setBackgroundTexture(background);
                gameMain.setBallTexture(ballImage);
                gameMain.setPaddleTexture(paddleImage);
                gameMain.setLevelDifficulty(Level.LevelDifficulty.ASIAN);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
            btnVeryHard.setOnAction(e3 -> {
                SoundEffect.playButtonClick();
                GameMain gameMain = new GameMain(primaryStage);
                gameMain.setBackgroundTexture(background);
                gameMain.setBallTexture(ballImage);
                gameMain.setPaddleTexture(paddleImage);
                gameMain.setLevelDifficulty(Level.LevelDifficulty.VERY_HARD);
                try {
                    gameMain.start(primaryStage); // chuyển sang màn game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            btnHard.setOnAction(e4 -> {
                SoundEffect.playButtonClick();
                GameMain gameMain = new GameMain(primaryStage);
                gameMain.setBackgroundTexture(background);
                gameMain.setBallTexture(ballImage);
                gameMain.setPaddleTexture(paddleImage);
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
            SoundEffect.playButtonClick();
            OptionsScreen.show(contentLayer, menuBox, background, ballImage, paddleImage);
        });

        // ====================== NÚT EXIT ======================
        btnExit.setOnAction(e3 -> {
            SoundEffect.playButtonClick();
            primaryStage.close();
        });
    }
}