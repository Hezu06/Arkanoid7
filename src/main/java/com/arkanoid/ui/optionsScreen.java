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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class optionsScreen {
    public static void show(StackPane contentLayer, VBox menuBox, ImageView background) {
        String pathBall = "";
        String pathPaddle = "";
        GameButton btnSetBackground = new GameButton("BACKGROUND");
        GameButton btnSetBall = new GameButton("BALL");
        GameButton btnSetPaddle = new GameButton("PADDLE");
        GameButton btnBack = new GameButton("BACK");

        GameButton btnBackground1 = new GameButton("btnBackground1");
        GameButton btnBackground2 = new GameButton("btnBackground2");
        GameButton btnBackground3 = new GameButton("btnBackground3");
        GameButton btnBackground4 = new GameButton("btnBackground4");
        GameButton btnBack1 = new GameButton("BACK");

        for (Button b : new Button[]{btnSetBackground, btnSetBall, btnSetPaddle, btnBack}) {
            ButtonEffects.applyHoverEffect(b);
        }

        VBox optionsBox = new VBox(30, btnSetBackground, btnSetBall, btnSetPaddle, btnBack);
        optionsBox.setAlignment(Pos.CENTER);
        fadeSmooth.smoothContent(contentLayer, optionsBox);
        btnSetBackground.setOnAction(e -> {
            for (Button b : new Button[]{btnBackground1, btnBackground2, btnBackground3, btnBackground4, btnBack1}) {
                ButtonEffects.applyHoverEffect(b);
            }

            //GameButton.setBackground(btnBackground1, "file:A:/Game java/Arkanoid7/src/main/resources/assets/Background/blackBackground.jpg");

            VBox backgroundsBox = new VBox(30, btnBackground1, btnBackground2, btnBackground3, btnBackground4, btnBack1);
            backgroundsBox.setAlignment(Pos.CENTER);
            fadeSmooth.smoothContent(contentLayer, backgroundsBox);

            btnBackground1.setOnAction(eB1 -> {
                background.setImage(new Image("file:A:/Game java/Arkanoid7/src/main/resources/assets/Background/galaxyBackground.jpg"));
            });

            btnBackground2.setOnAction(eB2 -> {
                background.setImage(new Image("file:A:/Game java/Arkanoid7/src/main/resources/assets/Background/blackBackground.jpg"));
            });

            btnBackground3.setOnAction(eB3 -> {
                background.setImage(new Image("file:A:/Game java/Arkanoid7/src/main/resources/assets/Background/whiteBackground.jpg"));
            });

            btnBackground4.setOnAction(eB4 -> {
                background.setImage(new Image("file:A:/Game java/Arkanoid7/src/main/resources/assets/Background/blackBackground.jpg"));
            });

            btnBack1.setOnAction(e1 -> fadeSmooth.smoothContent(contentLayer, optionsBox));
        });
        btnBack.setOnAction(e2 -> fadeSmooth.smoothContent(contentLayer, menuBox));
    }
}