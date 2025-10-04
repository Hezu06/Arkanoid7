package com.arkanoid.ui;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GameMenu extends Application {
    final int Width = 600;
    final int Height = 950;
//    static void applyHoverEffect(Button button) {
//        button.setOnMouseEntered(e -> {
//            button.setScaleX(1.1); // to hơn 10%
//            button.setScaleY(1.1);
//            button.setTextFill(Color.YELLOW); // chữ sáng lên
//        });
//
//        button.setOnMouseExited(e -> {
//            button.setScaleX(1.0); // trả lại bình thường
//            button.setScaleY(1.0);
//            button.setTextFill(Color.BLACK); // màu chữ gốc
//        });
//    }
    public void start(Stage primaryStage) {
        //Background
        StackPane root = new StackPane();
        Image image = new Image("images/Background/background.jpg");
        ImageView background = new ImageView(image);
        background.setFitWidth(700);
        background.setFitHeight(950);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(4), background);
        tt.setFromX(0);
        tt.setToX(-40); // trượt sang trái
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();
        root.getChildren().add(background);

        Label title = new Label("BRICK BREAKER");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setTextFill(Color.RED);

        Button btnPlay = new Button("Play");
        Button btnOptions = new Button("Options");
        Button btnExit = new Button("Exit");

        btnPlay.setPrefWidth(200);
        btnPlay.setPrefHeight(50);
        btnPlay.setFont(Font.font("Arial", 20));

        btnOptions.setPrefWidth(200);
        btnOptions.setPrefHeight(50);
        btnOptions.setFont(Font.font("Arial", 20));

        btnExit.setPrefWidth(200);
        btnExit.setPrefHeight(50);
        btnExit.setFont(Font.font("Arial", 20));

        VBox vbox = new VBox(30, title, btnPlay, btnOptions, btnExit);
        vbox.setAlignment(Pos.CENTER);

        root.getChildren().add(vbox);

        Scene scene = new Scene(root, Width, Height);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();
//        //effect
//        ButtonEffects.applyFadeEffect(btnPlay);
//        ButtonEffects.applyHoverEffect(btnPlay);
//
//        ButtonEffects.applyFadeEffect(btnOptions);
//        ButtonEffects.applyHoverEffect(btnOptions);
//
//        ButtonEffects.applyFadeEffect(btnExit);
//        ButtonEffects.applyHoverEffect(btnExit);
//Chooose Play
        btnPlay.setOnAction(e -> {
            Label startLabel = new Label("CHOOSE");
            startLabel.setFont(Font.font("Arial", FontWeight.BOLD, 80));
            startLabel.setTextFill(Color.GREEN);
//Hard
            Button btnHard = new Button("Hard");
            btnHard.setPrefWidth(200);
            btnHard.setPrefHeight(50);
            btnHard.setFont(Font.font("Arial", 20));
//Normal
            Button btnNormal = new Button("Normal");
            btnNormal.setPrefWidth(200);
            btnNormal.setPrefHeight(50);
            btnNormal.setFont(Font.font("Arial", 20));
//Easy
            Button btnEasy = new Button("Easy");
            btnEasy.setPrefWidth(200);
            btnEasy.setPrefHeight(50);
            btnEasy.setFont(Font.font("Arial", 20));
//back
            Button btnBack = new Button("Back");
            btnBack.setPrefWidth(70);
            btnBack.setPrefHeight(30);

            VBox gameRoot = new VBox(30, startLabel,btnHard, btnNormal, btnEasy, btnBack);
            gameRoot.setAlignment(Pos.CENTER);

            Scene gameScene = new Scene(gameRoot, Width, Height);

            primaryStage.setScene(gameScene);

            btnBack.setOnAction(e1 ->{
                primaryStage.setScene(scene);
            });
        });
//Choose Options
        btnOptions.setOnAction(e -> {
            Label optionsLabel = new Label("OPTIONS");
            optionsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 80));
            optionsLabel.setTextFill(Color.GREEN);

            Button btnBack = new Button("Back");
            btnBack.setPrefWidth(70);
            btnBack.setPrefHeight(30);

            VBox optionsRoot = new VBox(optionsLabel, btnBack);
            optionsRoot.setAlignment(Pos.CENTER);

            Scene optionsScene = new Scene(optionsRoot, Width, Height);

            primaryStage.setScene(optionsScene);

            btnBack.setOnAction(e2 ->{
                primaryStage.setScene(scene);
            });
        });

        btnExit.setOnAction(e -> {
            primaryStage.close();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
