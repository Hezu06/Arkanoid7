package com.arkanoid.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

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

    // Power-up states (optional)
    private boolean multiPowerupInEffect;
    private boolean firePowerupInEffect;
    private boolean slowPowerupInEffect;
    private boolean immortalPowerupInEffect;

    private static final int WINDOW_WIDTH = 750;

    public Paddle(double x, double y, String paddleType, double speed, Image image) {
        super(x, y, paddleType.equals("large") ? 120 : 100, 20, 0, 0);
        this.speed = speed;
        this.originalWidth = width;
        this.paddleType = paddleType;
        this.smallPaddleImage = image;
        this.largePaddleImage = image;

        removeAllPowerUpEffects();
    }

    public void update(double deltaTime) {
        move(deltaTime);

        // Hết hiệu ứng mở rộng → trở về kích thước ban đầu
        if (expanded && System.currentTimeMillis() > expandEndTime) {
            width = originalWidth;
            expanded = false;
            System.out.println("Paddle shrank!");
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
        if (paddleType.equals("large")) { return largePaddleImage; } return smallPaddleImage;
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
            // Nếu đang mở rộng, gia hạn thêm 5 giây
            expandEndTime = System.currentTimeMillis() + 5000;
        }
    }

    public void removeAllPowerUpEffects() {
        multiPowerupInEffect = false;
        firePowerupInEffect = false;
        slowPowerupInEffect = false;
        immortalPowerupInEffect = false;
        expanded = false;
        width = originalWidth;
    }

    // ---------------------
    // Movement controls
    // ---------------------
    public void setMovingLeft(boolean value) { movingLeft = value; }
    public void setMovingRight(boolean value) { movingRight = value; }

    public boolean isExpanded() { return expanded; }
    public void setExpanded(boolean expanded) { this.expanded = expanded; }
}
