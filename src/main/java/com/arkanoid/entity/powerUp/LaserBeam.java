package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.brick.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LaserBeam {
    private double x;
    private double y;
    private final double speed = 8;
    private final double width = 4;
    private final double height = 15;

    public LaserBeam(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y -= speed;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, width, height);
    }

    public double getY() {
        return y;
    }

    public boolean collidesWith(Brick brick) {
        return x < brick.getX() + brick.getWidth() &&
                x + width > brick.getX() &&
                y < brick.getY() + brick.getHeight() &&
                y + height > brick.getY();
    }
}
