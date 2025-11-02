package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.GameObject;
import com.arkanoid.game.GameMain;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public abstract class PowerUp extends GameObject {
    private final double fallSpeed = 3.0;
    private boolean active = true;

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;

    public PowerUp(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!active) return;
        if (image != null) {
            gc.drawImage(image, x, y, 32, 32);
        } else {
            gc.setFill(getColor());
            gc.fillOval(x, y, width, height);
            gc.setStroke(Color.WHITE);
            gc.strokeOval(x, y, width, height);
        }
    }

    @Override
    public void update() {
        double fallSpeed = 3.0;
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
        if (!active) return false;

        Rectangle2D powerUpRect = new Rectangle2D(x, y, width, height);
        Rectangle2D otherRect = new Rectangle2D(other.getX(), other.getY(), other.getWidth(), other.getHeight());

        return powerUpRect.intersects(otherRect);
    }

    // Hành động khi được paddle hứng
    public abstract void applyEffect(GameMain game);
}