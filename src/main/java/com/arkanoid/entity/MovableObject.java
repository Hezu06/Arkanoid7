package com.arkanoid.entity;

public abstract class MovableObject extends GameObject {
    protected double dx;
    protected double dy;

    public MovableObject(double x, double y, int width, int height, double dx, double dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
    }

    public abstract void move(double deltaTime);

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}