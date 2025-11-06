package com.arkanoid.ui;

import javafx.animation.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

class FadeSmooth {
    public static void smoothContent(StackPane contentLayer, VBox newContent) {
        if (!contentLayer.getChildren().isEmpty()) {
            VBox oldContent = (VBox) contentLayer.getChildren().getFirst();

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