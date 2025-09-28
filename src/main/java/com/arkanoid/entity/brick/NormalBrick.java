package com.arkanoid.entity.brick;

import javafx.scene.paint.Color;

public class NormalBrick extends Brick {
    public NormalBrick(double x, double y, double width, double height) {
        super(x, y, width, height, 1, Color.GRAY);
    }

    @Override
    protected void updateAppearance() {}
}