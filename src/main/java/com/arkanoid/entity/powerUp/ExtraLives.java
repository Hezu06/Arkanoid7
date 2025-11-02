package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.Objects;

public class ExtraLives extends PowerUp {
    public ExtraLives(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/green.png"))));
    }

    @Override
    protected Color getColor() {
        return Color.RED;
    }

    @Override
    public void applyEffect(GameMain game) {
        game.getGameStateManager().increaseLives();

        System.out.println("+1 Life!");
    }
}