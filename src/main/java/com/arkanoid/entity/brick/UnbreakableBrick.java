package com.arkanoid.entity.brick;

import javafx.scene.paint.Color;
// Color for showing brick

public class UnbreakableBrick extends Brick {
    public UnbreakableBrick(int x, int y, double width, double height, int gridX, int gridY) {
        super(x, y, width, height, gridX, gridY, Integer.MAX_VALUE, Color.DARKCYAN);
    }

    @Override
    public boolean takeHit() {
        return false;       // Never break
    }
    @Override
    protected void updateAppearance() {
        /* No change due to unbreakability */
    }
}