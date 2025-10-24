package com.arkanoid.ui;

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

public class GameMenu extends Application {
    static final int Width = 750;
    static final int Height = 950;
    static final int widthBackground = 850;
    static final int heightBackground = 950;
    public static String pathBackground = "file:A:/Game java/Arkanoid7/src/main/resources/assets/Background/galaxyBackground.jpg";
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
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setTextFill(Color.RED);

        GameButton btnPlay = new GameButton("PLAY");
        GameButton btnOptions = new GameButton("OPTIONS");
        GameButton btnExit = new GameButton("EXIT");

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
            startLabel.setFont(Font.font("Arial", FontWeight.BOLD, 80));
            startLabel.setTextFill(Color.GREEN);

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
        });

        // ====================== NÚT OPTIONS ======================
        btnOptions.setOnAction(e2 -> {
            optionsScreen.show(contentLayer, menuBox, background);
        });

        // ====================== NÚT EXIT ======================
        btnExit.setOnAction(e3 -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
}