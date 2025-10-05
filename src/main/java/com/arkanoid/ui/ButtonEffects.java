package com.arkanoid.ui;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ButtonEffects {

    public static void applyFadeEffect(Button button, double from, double to) {
        button.setOpacity(from);
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), button);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.play();
    }

    public static void applyHoverEffect(Button button) {
        button.setOnMouseEntered(e -> {
            applyFadeEffect(button, 0.6, 1.0);
            button.setScaleX(1.1);
            button.setScaleY(1.1);
            button.setTextFill(Color.YELLOW);
        });

        button.setOnMouseExited(e -> {
            applyFadeEffect(button, 1.0, 0.6);
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            button.setTextFill(Color.BLACK);
        });
    }
}