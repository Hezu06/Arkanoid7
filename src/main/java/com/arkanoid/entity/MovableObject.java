package com.arkanoid.entity;

import java.awt.*;

public abstract class MovableObject extends GameObject {
    protected double dx;
    protected double dy;

    public MovableObject(double x, double y, int width, int height, double dx, double dy) {
        super(x, y, width, height);
        this.dy = dy;
    }

    public void update() {

    }

    public void render(Graphics g) {

    }

    public abstract void move();
}