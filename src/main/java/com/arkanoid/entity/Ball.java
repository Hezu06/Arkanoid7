package com.arkanoid.entity;

import javafx.geometry.Rectangle2D;

import java.awt.*;

public class Ball extends MovableObject {
    private double speed;
    private double directionX;
    private double directionY;
    private double radius;

    public Ball(double x, double y, int width, int height, double dx, double dy, double speed,
                double directionX, double directionY,  double radius) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.radius = radius;
    }

    public void bounceOff(GameObject other) {
        if (checkCollision(other)) {
            Rectangle2D rect = other.getBounds();

            double cx = getCenterX();
            double cy = getCenterY();

            double closetX = Math.max(rect.getMinX(), Math.min(cx, rect.getMaxX()));
            double closetY = Math.max(rect.getMinY(), Math.min(cy, rect.getMaxY()));

            double dxBall = cx - closetX;
            double dyBall = cy - closetY;

            double vx = directionX * speed;
            double vy = directionY * speed;

            double nx = dxBall;
            double ny = dyBall;

            double length = Math.sqrt(nx * nx + ny * ny);
            if (length != 0) {
                nx = nx / length;
                ny = ny / length;
            }

            double dot = vx * nx + vy * ny;
            vx = vx - 2 * dot * nx;
            vy = vy - 2 * dot * ny;

            double vLength = Math.sqrt(vx * vx + vy * vy);

            directionX = vx / vLength;
            directionY = vy / vLength;
        }
    }

    public boolean checkCollision(GameObject other) {
        Rectangle2D rect = other.getBounds();

        double cx = getCenterX();
        double cy = getCenterY();

        double closetX = Math.max(rect.getMinX(), Math.min(cx, rect.getMaxX()));
        double closetY = Math.max(rect.getMinY(), Math.min(cy, rect.getMaxY()));

        double dx = cx  - closetX;
        double dy = cy - closetY;

        return (dx * dx + dy * dy) <= (radius * radius);
    }

    public void move() {
        x += directionX * speed;
        y += directionY * speed;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(
                x - radius,
                y - radius,
                radius * 2,
                radius * 2
        );
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getCenterX() {
        return x + radius;
    }
    public double getCenterY() {
        return  y + radius;
    }

}