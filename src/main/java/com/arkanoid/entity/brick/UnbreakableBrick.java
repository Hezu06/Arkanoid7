package com.arkanoid.entity.brick;

public class UnbreakableBrick extends Brick {
    private static final String UNBREAKABLE_BRICK_IMAGE = "/assets/Bricks/UnbreakableBrick.png";

    public UnbreakableBrick(double x, double y, double width, double height, int gridX, int gridY) {
        super(x, y, width, height, gridX, gridY, Integer.MAX_VALUE, UNBREAKABLE_BRICK_IMAGE);
    }

    @Override
    public boolean takeHit() {
        return false; // Never break
    }
    @Override
    protected void updateAppearance() {
        /* No change due to unbreakability */
    }
}