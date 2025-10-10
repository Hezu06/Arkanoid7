package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.GameObject;
import com.arkanoid.game.GameMain;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;

public abstract class PowerUp extends GameObject {
    private double fallSpeed = 3.0;
    private boolean active = true;

    public PowerUp(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        gc.setFill(getColor());
        gc.fillOval(x, y, width, height);
        gc.setStroke(Color.WHITE);
        gc.strokeOval(x, y, width, height);
    }

    @Override
    public void update() {
        y += fallSpeed;

        if (y > 900) {
            active = false;
        }
    }

    @Override
    public boolean takeHit() {
        if (active) {
            active = false;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    protected Color getColor() {
        return null;
    }

    public boolean checkCollision(GameObject other) {
        return true;
    }

    // Hành động khi được paddle hứng
    public abstract void applyEffect(GameMain game);
}