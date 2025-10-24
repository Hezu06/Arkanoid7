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

class fadeSmooth {
    public static void smoothContent(StackPane contentLayer, VBox newContent) {
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
}