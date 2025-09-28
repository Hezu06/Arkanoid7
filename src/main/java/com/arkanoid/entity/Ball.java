package com.arkanoid.entity;

import javafx.geometry.Rectangle2D;

public class Ball extends MovableObject {
    private double speed;
    private double radius;

    public Ball(double x, double y, double dx, double dy, double speed, double radius) {
        super(x, y, (int)(2 * radius), (int)(2 * radius), dx, dy);
        this.speed = speed;
        this.radius = radius;
    }

    public void bounceOff(GameObject other) {
        Rectangle2D rect = other.getBounds();

        double cx = getCenterX();
        double cy = getCenterY();

        double closestX = Math.max(rect.getMinX(), Math.min(cx, rect.getMaxX()));
        double closestY = Math.max(rect.getMinY(), Math.min(cy, rect.getMaxY()));

        double dxBall = cx - closestX;
        double dyBall = cy - closestY;

        double length = Math.sqrt(dxBall * dxBall + dyBall * dyBall);
        double nx, ny;
        if (length == 0) {
            // nếu tâm ball nằm đúng trong object, lấy pháp tuyến dựa trên vận tốc hiện tại
            double norm = Math.sqrt(dx * dx + dy * dy);
            nx = dx / norm;
            ny = dy / norm;
        } else {
            nx = dxBall / length;
            ny = dyBall / length;
        }

        double vx = dx * speed;
        double vy = dy * speed;
        double dot = vx * nx + vy * ny;
        vx = vx - 2 * dot * nx;
        vy = vy - 2 * dot * ny;

        double vLength = Math.sqrt(vx * vx + vy * vy);
        if (vLength != 0) {
            dx = vx / vLength;
            dy = vy / vLength;
        }

        double overlap = radius - length;
        if (overlap > 0) {
            x += nx * (overlap + 1);
            y += ny * (overlap + 1);
        }
    }

    public boolean checkCollision(GameObject other) {
        Rectangle2D rect = other.getBounds();

        double cx = getCenterX();
        double cy = getCenterY();

        double closestX = Math.max(rect.getMinX(), Math.min(cx, rect.getMaxX()));
        double closestY = Math.max(rect.getMinY(), Math.min(cy, rect.getMaxY()));

        double dxBall = cx  - closestX;
        double dyBall = cy - closestY;

        return (dxBall * dxBall + dyBall * dyBall) <= (radius * radius);
    }

    public void move() {
        x += dx * speed;
        y += dy * speed;
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