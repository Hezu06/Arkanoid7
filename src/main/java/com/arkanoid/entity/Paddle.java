package com.arkanoid.entity;


import javafx.scene.canvas.GraphicsContext;

public class Paddle extends MovableObject {
    protected double speed;


    public Paddle(double x, double y, int width, int height, double dx, double dy) {
        super(x, y, width, height, dx, dy);
    }


    @Override
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