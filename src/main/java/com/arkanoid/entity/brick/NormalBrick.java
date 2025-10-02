package com.arkanoid.entity.brick;

import javafx.scene.paint.Color;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y, double width, double height, int gridX, int gridY) {
        super(x, y, width, height, gridX, gridY, 1, Color.GRAY);
    }

    @Override
    protected void updateAppearance() {
        /* Normal bricks so NO CHANGE */
    }
}