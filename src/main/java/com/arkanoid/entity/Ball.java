package com.arkanoid.entity;

import javafx.scene.canvas.GraphicsContext;

public class Ball extends MovableObject {
    private double speed;
    private double directionX;
    private double directionY;

    public Ball(double x, double y, int width, int height, double dx, double dy, double speed,
                double directionX, double directionY) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void bounceOff(GameObject other) {
        
    }

    public void checkCollision(GameObject other) {
    }

    public void move() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public boolean takeHit() {
        return false;
    }
}