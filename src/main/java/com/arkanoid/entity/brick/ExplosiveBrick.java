package com.arkanoid.entity.brick;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class ExplosiveBrick extends Brick {

    private static final String EXPLOSIVE_BRICK_IMAGE = "/assets/Bricks/ExplosiveBrick.png";

    public ExplosiveBrick(double x, double y, double width, double height, int gridX, int gridY) {
        super(x, y, width, height, gridX, gridY, 1, EXPLOSIVE_BRICK_IMAGE);
    }

    @Override
    public boolean takeHit() {
        return super.takeHit();
    }

    @Override
    public List<int[]> triggerSpecialAction() {
        List<int[]> affectedCoords = new ArrayList<>();

        // Iterate through a 3x3 area relative to this brick's center.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Add the grid coordinates of the blast radius.
                affectedCoords.add(new int[]{this.getGridX() + dx, this.getGridY() + dy});
            }
        }

        return affectedCoords;
    }


    @Override
    public void updateAppearance() {
        /* No change since it breaks immediately after 1 hit */
    }

    @Override
    protected Color getGlowColor() {
        return Color.RED.darker();
    }
}