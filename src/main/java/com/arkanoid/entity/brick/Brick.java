package com.arkanoid.entity.brick;

import com.arkanoid.entity.GameObject;
import com.arkanoid.entity.powerUp.PowerUp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Brick extends GameObject {

//    protected final int gridX, gridY;   // Handling matrix-related issue (Eg: Explosive 3x3).
    protected int currentDurability;
    protected final int maxDurability;
    protected boolean isBroken = false;
//    protected PowerUpType powerUp;
//    protected boolean dropsPowerUp = false;
    protected Color color;

    public Brick(int x, int y, int width, int height, int maxDurability, Color color) {
        super(x, y, width, height);
        this.maxDurability = maxDurability;
        this.currentDurability = maxDurability;

    }


}