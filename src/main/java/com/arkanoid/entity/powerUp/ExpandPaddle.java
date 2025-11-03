package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class ExpandPaddle extends PowerUp {
    public ExpandPaddle(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/expandPaddle.png"))));
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
