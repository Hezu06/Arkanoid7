package com.arkanoid.entity.brick;

import com.arkanoid.entity.GameObject;
import com.arkanoid.entity.powerUp.PowerUp.PowerUpType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public abstract class Brick extends GameObject {

    protected final int gridX, gridY;   // Identify bricks by numbering them. gridX = cols, gridY = rows.
    protected int currentDurability;
    protected final int maxDurability;
    protected boolean isBroken = false;

    protected Image textureImage;

    // PowerUp related attributes.
    protected PowerUpType powerUpContent = null;
    protected boolean dropsPowerUp = false;

    public Brick(double x, double y, double width, double height, int gridX, int gridY, int maxDurability, String imagePath) {
        super(x, y, width, height);
        this.gridX = gridX;
        this.gridY = gridY;
        this.maxDurability = maxDurability;
        this.currentDurability = maxDurability;

        loadTexture(imagePath);
    }

    @Override
    public void update() {
        /* No update applied for static objects */
    }

    protected void loadTexture(String imagePath) {
        try {
            textureImage = new Image(imagePath, width, height, false, true);
        } catch (Exception e) {
            System.err.printf("Failed to load image at path: %s. Falling back to color rendering.\n", imagePath);
            this.textureImage = null;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isBroken) {

            if (textureImage != null) {
                gc.drawImage(textureImage, x, y, width, height);
            } else {
                gc.setFill(Color.RED);
            }

            gc.setStroke(Color.BLACK);
            gc.strokeRect(this.x, this.y, width, height);
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

    protected void updateTexture(String imagePath) {
        loadTexture(imagePath);
    }

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