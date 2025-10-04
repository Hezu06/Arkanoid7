package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class PowerUp extends GameObject {

    private PowerUpType type;

    @Override
    public void render(GraphicsContext gc) {

    }

    public PowerUp(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean takeHit() {
        return false;
    }

    public enum PowerUpType {
        EXTRA_LIVES, MULTI_BALLS, FIRE_BALLS, EXPAND_PADDLE, IMMORTAL, EXTRA_COINS
    }
}