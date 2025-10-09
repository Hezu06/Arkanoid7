package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.scene.paint.Color;

public class MultiBall extends PowerUp {
    public MultiBall(double x, double y) {
        super(x, y, 20, 20);
    }

    @Override
    protected Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public void applyEffect(GameMain game) {
        game.spawnExtraBalls();
    }
}
