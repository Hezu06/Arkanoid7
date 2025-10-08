package com.arkanoid.ui;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameButton extends Button {

    public GameButton(String text) {
        super(text);

        setPrefSize(250, 70);
        setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        setTextFill(Color.WHITE);
        setOpacity(0.6); // để đồng bộ với ButtonEffects

        // Style cơ bản: nền tím gradient, viền trắng, bo góc
        setStyle(
                "-fx-background-color: linear-gradient(to bottom, #a74eff, #37006b);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 12;" +
                        "-fx-cursor: hand;"
        );

        // Bóng nền của nút (đổ ra ngoài)
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setColor(Color.rgb(255, 255, 255, 0.4));
        setEffect(shadow);

        // Hiệu ứng ánh sáng phát quanh chữ
        DropShadow textGlow = new DropShadow();
        textGlow.setOffsetX(0);
        textGlow.setOffsetY(0);
        textGlow.setRadius(20);
        textGlow.setColor(Color.rgb(180, 100, 255, 0.9)); // tím sáng

        // Hiệu ứng Glow tăng cường ánh sáng chữ
        Glow glow = new Glow(0.4);

        // Kết hợp hiệu ứng Glow + DropShadow cho chữ
        textGlow.setInput(glow);
        setEffect(textGlow);
    }
}