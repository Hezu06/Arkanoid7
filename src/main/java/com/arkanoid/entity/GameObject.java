package com.arkanoid.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;

// GameObject class
public abstract class GameObject {
    protected double x, y;
    protected double width;
    protected double height;
    protected Rectangle rect;

    public GameObject(double posX, double posY, double width, double height) {
        this.x = posX;
        this.y = posY;
        this.width = width;
        this.height = height;
        this.rect = new Rectangle(posX, posY, width, height);
    }

    public abstract void update();
    public abstract void render(GraphicsContext gc);
    public abstract boolean takeHit();

    // Getters and Setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getRect() { return this.rect; }

    protected Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }
}