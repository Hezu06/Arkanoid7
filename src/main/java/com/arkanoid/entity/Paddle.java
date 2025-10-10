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
    private boolean expanded  = false;
    private long expandEndTime = 0;

    // Powerup states
    private boolean multiPowerupInEffect;
    private boolean expandPowerupInEffect;
    private boolean firePowerupInEffect;
    private boolean slowPowerupInEffect;
    private boolean immortalPowerupInEffect;

    public Paddle(double x, double y, String paddleType, double speed) {
        super(x, y, paddleType.equals("large") ? 500 : 80, 20, 0, 0);
        this.paddleType = paddleType;
        this.speed = speed;

        this.smallPaddleImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Paddle/defaultPaddle.png")));
        this.largePaddleImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Paddle/grassPaddle.png")));

        removeAllPowerupEffects();
    }

    public void update(double deltaTime) {
        move(deltaTime);
        rect.setX(x);
        rect.setY(y);

        if (expanded && System.currentTimeMillis() > expandEndTime) {
            width /= 1.6;
            expanded = false;
            System.out.println("Paddle nho lai roi");
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
//        x += dx * speed;
        if (x < 0) x = 0;
        if (x + width > 750) x = 750 - width;
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
    }

    public boolean isExpaned() {
        return expanded;
    }

    public void setExpaned(boolean expaned) {
        this.expanded = expaned;
    }

    public void expandTemporarily() {
        if(!expanded) {
            expanded = true;
            width *= 1.6;
            expandEndTime = System.currentTimeMillis() + 7000;
            System.out.println("Paddle to roi");
        } else {
            expandEndTime = System.currentTimeMillis() + 5000;
        }
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

    public void setMovingLeft(boolean value) {
        movingLeft = value;
    }

    public void setMovingRight(boolean value) {
        movingRight = value;
    }

}