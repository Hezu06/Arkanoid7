package com.arkanoid.entity;


public class Paddle extends MovableObject {
    protected double speed;


    public Paddle(double x, double y, int width, int height, double dx, double dy) {
        super(x, y, width, height, dx, dy);
    }


    @Override
    public void move() {

    }
}