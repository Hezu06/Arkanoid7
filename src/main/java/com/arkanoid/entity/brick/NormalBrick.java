package com.arkanoid.entity.brick;

import javafx.scene.paint.Color;

public class NormalBrick extends Brick {

    private static final String NORMAL_BRICK_IMAGE = "/assets/Bricks/NormalBrick.png";

    public NormalBrick(double x, double y, double width, double height, int gridX, int gridY) {
        super(x, y, width, height, gridX, gridY, 1, NORMAL_BRICK_IMAGE);
    }

    @Override
    protected void updateAppearance() {
        /* Normal bricks so NO CHANGE */
    }

    @Override
    protected Color getGlowColor() {
        return Color.HOTPINK.darker();
    }
}