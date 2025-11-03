package com.arkanoid.ui;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameButton extends Button {

    private static final String FONT_PATH = "/fonts/GameFont.TTF";

    public GameButton(String text) {
        super(text);

        setPrefSize(250, 70);
        setFont(Font.loadFont(
                getClass().getResourceAsStream(FONT_PATH), 36));
        setTextFill(Color.BLACK);
        setOpacity(0.6); // sync w/ ButtonEffects

        // Default style
        setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 12;" +
                        "-fx-cursor: hand;"
        );

        // Hiệu ứng ánh sáng tím
        DropShadow textGlow = new DropShadow();
        textGlow.setOffsetX(0);
        textGlow.setOffsetY(0);
        textGlow.setRadius(20);
        textGlow.setColor(Color.rgb(180, 100, 255, 0.9));
        Glow glow = new Glow(0.4);
        textGlow.setInput(glow);
        setEffect(textGlow);
    }
}
