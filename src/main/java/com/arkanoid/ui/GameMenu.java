package com.arkanoid.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameMenu extends Application {
    public void start(Stage primaryStage) {
        Label title = new Label("BRICK BREAKER");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setTextFill(Color.DARKBLUE);

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

        Scene scene = new Scene(vbox, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();

        btnPlay.setOnAction(e -> {
            Label startLabel = new Label("START");
            startLabel.setFont(Font.font("Arial", FontWeight.BOLD, 80));
            startLabel.setTextFill(Color.GREEN);

            VBox gameRoot = new VBox(startLabel);
            gameRoot.setAlignment(Pos.CENTER);

            Scene gameScene = new Scene(gameRoot, 800, 600);

            primaryStage.setScene(gameScene);
        });

        btnOptions.setOnAction(e -> {
            Label optionsLabel = new Label("OPTIONS");
            optionsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 80));
            optionsLabel.setTextFill(Color.GREEN);

            VBox optionsRoot = new VBox(optionsLabel);
            optionsRoot.setAlignment(Pos.CENTER);

            Scene optiomsScene = new Scene(optionsRoot, 800, 600);

            primaryStage.setScene(optiomsScene);
        });

        btnExit.setOnAction(e -> {
            primaryStage.close();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
