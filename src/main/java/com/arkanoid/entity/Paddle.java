package com.arkanoid.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Paddle extends MovableObject {
    private Image smallPaddleImage;
    private Image largePaddleImage;

    private String paddleType;
    private double speed;

    // Powerup states
    private boolean multiPowerupInEffect;
    private boolean expandPowerupInEffect;
    private boolean firePowerupInEffect;
    private boolean slowPowerupInEffect;
    private boolean immortalPowerupInEffect;

    public Paddle(double x, double y, String paddleType, double speed) {
        super(x, y, paddleType.equals("large") ? 120 : 60, 20, 0, 0);
        this.paddleType = paddleType;
        this.speed = speed;

        this.smallPaddleImage = new Image("defaultPaddle.png");
        this.largePaddleImage = new Image("sandPaddle.png");

        removeAllPowerupEffects();
    }

    @Override
    public void update() {
        move();
        rect.setX(x);
        rect.setY(y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImage(), x, y, width, height);
    }

    @Override
    public boolean takeHit() {
        return false;
    }

    @Override
    public void move() {
        x += dx * speed;
        if (x < 0) x = 0;
        if (x + width > 800) x = 800 - width;
        rect.setX(x);
    }

    private Image getImage() {
        if (paddleType.equals("large")) {
            return largePaddleImage;
        }
        return smallPaddleImage;
    }

    public void removeAllPowerupEffects() {
        multiPowerupInEffect = false;
        expandPowerupInEffect = false;
        firePowerupInEffect = false;
        slowPowerupInEffect = false;
        immortalPowerupInEffect = false;
        paddleType = "small";
        width = 60;
    }

    public void moveLeft() {
        dx = -1;
    }

    public void moveRight() {
        dx = 1;
    }

    public void stop() {
        dx = 0;
    }
}

