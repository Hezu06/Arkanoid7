package com.arkanoid.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Paddle extends MovableObject {
    private final Image smallPaddleImage;
    private final Image largePaddleImage;
    private String paddleType;

    private final double speed;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    private boolean expanded = false;
    private long expandEndTime = 0;
    private double originalWidth;

    private long laserEndTime = 0;
    private boolean laserInterrupted = false;
    private boolean laserActive = false;
    private boolean laserPowerupInEffect = false;

    private long immortalEndTime = 0;  // thời điểm hết hiệu lực Immortal
    private boolean immortalPowerupInEffect = false;

    private static final int WINDOW_WIDTH = 750;

    public Paddle(double x, double y, int width, double speed, Image image) {
        super(x, y, width, 20, 0, 0);
        this.speed = speed;
        this.originalWidth = width;
        this.paddleType = paddleType;
        this.smallPaddleImage = image;
        this.largePaddleImage = image;

        removeAllPowerUpEffects();
    }

    public void update(double deltaTime) {
        move(deltaTime);

        // Hết hiệu ứng mở rộng
        if (expanded && System.currentTimeMillis() > expandEndTime) {
            width = originalWidth;
            expanded = false;
            System.out.println("Paddle shrank!");
        }

        // Hết hiệu ứng laser
        if (laserActive && System.currentTimeMillis() > laserEndTime) {
            laserActive = false;
            System.out.println("Laser mode ended!");
        }

        // Hết hiệu ứng bất tử
        if (immortalPowerupInEffect && System.currentTimeMillis() > immortalEndTime) {
            immortalPowerupInEffect = false;
            System.out.println("Immortal mode ended!");
        }
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
    public void move(double deltaTime) {
        if (movingLeft) x -= speed * deltaTime;
        if (movingRight) x += speed * deltaTime;

        if (x < 0) x = 0;
        if (x + width > WINDOW_WIDTH) x = WINDOW_WIDTH - width;
    }

    private Image getImage() {
        return largePaddleImage;
    }

    // ---------------------
    // Power-up control
    // ---------------------

    public void expandTemporarily() {
        if (!expanded) {
            expanded = true;
            width = Math.min(originalWidth * 1.6, 300);
            expandEndTime = System.currentTimeMillis() + 7000;
            System.out.println("Paddle expanded!");
        } else {
            expandEndTime = System.currentTimeMillis() + 5000;
        }
    }

    public void activateLaserTemporarily(long durationMs) {
        laserActive = true;
        laserEndTime = System.currentTimeMillis() + durationMs;
        System.out.println("Laser mode activated for " + (durationMs / 1000) + "s!");
    }

    public void activateImmortalTemporarily(long durationMs) {
        immortalPowerupInEffect = true;
        immortalEndTime = System.currentTimeMillis() + durationMs;
        System.out.println("Immortal mode activated for " + (durationMs / 1000) + "s!");
    }

    public void removeAllPowerUpEffects() {
        expanded = false;
        width = originalWidth;
        laserActive = false;
        laserPowerupInEffect = false;
        immortalPowerupInEffect = false;
    }

    // ---------------------
    // Getters & Setters
    // ---------------------

    public boolean isLaserActive() { return laserActive; }

    public void setLaserActive(boolean laserActive) { this.laserActive = laserActive; }

    public boolean isLaserPowerupInEffect() { return laserPowerupInEffect; }

    public void setLaserPowerupInEffect(boolean value) { this.laserPowerupInEffect = value; }

    public boolean isLaserInterrupted() { return laserInterrupted; }

    public void setLaserInterrupted(boolean laserInterrupted) { this.laserInterrupted = laserInterrupted; }

    public boolean isExpanded() { return expanded; }

    public void setExpanded(boolean expanded) { this.expanded = expanded; }

    public boolean isImmortalPowerupInEffect() { return immortalPowerupInEffect; }

    public void setImmortalPowerupInEffect(boolean immortalPowerupInEffect) {
        this.immortalPowerupInEffect = immortalPowerupInEffect;
    }

    public void setMovingLeft(boolean value) { movingLeft = value; }

    public void setMovingRight(boolean value) { movingRight = value; }
}
