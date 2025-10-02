package com.arkanoid.entity.brick;

import javafx.scene.paint.Color;

public class ExplosiveBrick extends Brick {

    private boolean readyToExplode = false;

    public ExplosiveBrick(double x, double y, double width, double height, int gridX, int gridY) {
        super(x, y, width, height, gridX, gridY, 1, Color.RED);
    }

    @Override
    public boolean takeHit() {
        boolean broken = super.takeHit();
        if (broken) {
            readyToExplode = true;
        }
        return broken;
    }

    public boolean isReadyToExplode() {
        return readyToExplode;
    }

    @Override
    public boolean triggerSpecialAction() {
        readyToExplode = false;
        return true;
    }

    @Override
    public void updateAppearance() {
        /* No change since it breaks immediately after 1 hit */
    }
}