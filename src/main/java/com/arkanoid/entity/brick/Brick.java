package com.arkanoid.entity.brick;

import com.arkanoid.entity.GameObject;
import com.arkanoid.entity.powerUp.PowerUp.PowerUpType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public abstract class Brick extends GameObject {

    protected final int gridX, gridY;   // Identify bricks by numbering them. gridX = cols, gridY = rows.
    protected int currentDurability;
    protected final int maxDurability;
    protected boolean isBroken = false;
    protected Color color;

    // PowerUp related attributes.
    protected PowerUpType powerUpContent = null;
    protected boolean dropsPowerUp = false;

    public Brick(double x, double y, double width, double height, int gridX, int gridY, int maxDurability, Color color) {
        super(x, y, width, height);
        this.gridX = gridX;
        this.gridY = gridY;
        this.maxDurability = maxDurability;
        this.currentDurability = maxDurability;
        this.color = color;
    }

    @Override
    public void update() {
        /* No update applied for static objects */
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isBroken) {
            // Fill color.
            gc.setFill(color);
            gc.fillRect(x, y, width, height);

            // Draw border.
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, width, height);

            // Draw durability count for multi-hit bricks for TESTING.
            if (maxDurability > 1 && currentDurability > 0) {
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                String text = String.valueOf(currentDurability);

                // Text centering.
                double textWidth = text.length() * 6;
                gc.fillText(text, x + width / 2 - textWidth / 2, y + height / 2 + 5);
            }
        }
    }

    @Override
    public boolean takeHit() {
        if (!isBroken &&  currentDurability > 0) {
            currentDurability--;

            if (currentDurability <= 0) {
                isBroken = true;
                return true;
            }
            updateAppearance();
        }
        return false;
    }

    protected abstract void updateAppearance();

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setCurrentDurability(int currentDurability) {
        this.currentDurability = currentDurability;
    }

    public void setPowerUpDrop(PowerUpType type) {
        this.dropsPowerUp = true;
        this.powerUpContent = type;
    }
    public PowerUpType getPowerUpContent() { return powerUpContent; }

    public boolean triggerSpecialAction() {
        return false;
    }
}