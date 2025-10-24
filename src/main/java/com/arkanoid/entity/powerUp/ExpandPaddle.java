package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import com.arkanoid.entity.Paddle;

public class ExpandPaddle extends PowerUp {
    private final double maxLength = 300;

    public ExpandPaddle(double x, double y) {
        super(x, y, 20, 20);
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
