package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import com.arkanoid.entity.Paddle;

import java.util.Objects;

public class ExpandPaddle extends PowerUp {
    private final double maxLength = 300;

    public ExpandPaddle(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/blue.png"))));
    }

    @Override
    protected Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void applyEffect(GameMain game) {
        game.getPaddle().expandTemporarily();
        deactivate();
    }
}
