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
        setFont(Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"), 36));
        setTextFill(Color.BLACK);
        setOpacity(0.6); // để đồng bộ với ButtonEffects

        // Style mặc định
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

//    public static void setBackground(Button btn, String pathBackgroundButton) {
//        btn.setStyle(
//                "-fx-background-image: url('file:" + pathBackgroundButton + "');" +
//                        "-fx-background-size: 100% 100%;" +          // Ảnh phủ kín toàn bộ
//                        "-fx-background-repeat: no-repeat;" +
//                        "-fx-background-position: center center;" +
//                        "-fx-background-radius: 12;" +               // Giữ bo góc
//                        "-fx-border-radius: 12;" +
//                        "-fx-background-color: transparent;" +
//                        "-fx-border-color: transparent;" +
//                        "-fx-focus-color: transparent;" +
//                        "-fx-faint-focus-color: transparent;" +
//                        "-fx-padding: 0;" +
//                        "-fx-cursor: hand;"                           // Giữ hiệu ứng chuột
//        );
//    }
}
