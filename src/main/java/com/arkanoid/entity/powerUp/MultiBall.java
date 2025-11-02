package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class MultiBall extends PowerUp {
    public MultiBall(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/multiBall.png"))));
    }

    @Override
    protected Color getColor() {
        return Color.HOTPINK;
    }

    @Override
    public void applyEffect(GameMain game) {
        game.spawnExtraBalls();
    }
}
