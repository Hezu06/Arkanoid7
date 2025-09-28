package com.arkanoid.entity.brick;

import com.arkanoid.entity.GameObject;
import com.arkanoid.entity.powerUp.PowerUp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public abstract class Brick extends GameObject {

//    protected final int gridX, gridY;   // Handling matrix-related issue (Eg: Explosive 3x3).
    protected int currentDurability;
    protected final int maxDurability;
    protected boolean isBroken = false;
//    protected PowerUpType powerUp;
//    protected boolean dropsPowerUp = false;
    protected Color color;

    public Brick(double x, double y, double width, double height, int maxDurability, Color color) {
        super(x, y, width, height);
//        this.gridX = gridX;
//        this.gridY = gridY;
        this.maxDurability = maxDurability;
        this.currentDurability = maxDurability;
        this.color = color;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isBroken) {
            gc.setFill(color);
            gc.fillRect(x, y, width, height);

            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, width, height);

            if (maxDurability > 1 && currentDurability > 0) {
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                String text = String.valueOf(currentDurability);

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

    public boolean isBroken() {
        return isBroken;
    }
}