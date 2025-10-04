package com.arkanoid.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Ball extends MovableObject {
    private final double speed;
    private final double radius;
    private final Image image;
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 800;

    public Ball(double x, double y, double dx, double dy, double speed, double radius) {
        super(x, y, (int) (2 * radius), (int) (2 * radius), dx, dy);
        this.speed = speed;
        this.radius = radius;
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Ball/defaultball.png")));
    }

    public void bounceOff(GameObject other) {
        Rectangle2D rect = other.getBounds();

        double ballCenterX = getCenterX();
        double ballCenterY = getCenterY();

        double closestX = Math.max(rect.getMinX(), Math.min(ballCenterX, rect.getMaxX()));
        double closestY = Math.max(rect.getMinY(), Math.min(ballCenterY, rect.getMaxY()));

        double diffX = ballCenterX - closestX;
        double diffY = ballCenterY - closestY;

        // --- Va chạm với Paddle ---
        if (other instanceof Paddle paddle) {
            double hitPos = (ballCenterX - paddle.getX()) / paddle.getWidth();
            hitPos = Math.max(0.0, Math.min(hitPos, 1.0)); // giới hạn trong [0,1]

            // Góc lệch ±60°
            double angle = Math.toRadians(120 * (hitPos - 0.5));

            dx = Math.sin(angle);
            dy = -Math.cos(angle);

            // Đặt bóng lên trên paddle một chút
            y = paddle.getY() - radius * 2 - 1;
            return;
        }

        // --- Va chạm với Brick hoặc tường ---
        double absX = Math.abs(diffX);
        double absY = Math.abs(diffY);

        // So sánh khoảng cách trục để xác định hướng va chính
        if (absX > absY) {
            dx = -dx; // Va bên trái/phải
            // Dịch bóng ra khỏi gạch để tránh "lọt vào"
            if (diffX > 0) {
                x = rect.getMaxX() + 1;
            } else {
                x = rect.getMinX() - radius * 2 - 1;
            }
        } else {
            dy = -dy; // Va trên/dưới
            if (diffY > 0) {
                y = rect.getMaxY() + 1;
            } else {
                y = rect.getMinY() - radius * 2 - 1;
            }
        }
    }


    public boolean checkCollision(GameObject other) {
        Rectangle2D rect = other.getBounds();

        double cx = getCenterX();
        double cy = getCenterY();

        double closestX = Math.max(rect.getMinX(), Math.min(cx, rect.getMaxX()));
        double closestY = Math.max(rect.getMinY(), Math.min(cy, rect.getMaxY()));

        double dxBall = cx - closestX;
        double dyBall = cy - closestY;

        return (dxBall * dxBall + dyBall * dyBall) <= (radius * radius);
    }

    public double getCenterX() {
        return x + radius;
    }

    public double getCenterY() {
        return y + radius;
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

    @Override
    public void render(GraphicsContext gc) {
        if (image.isError()) {
            System.out.println("Can't render ball");
        }
        gc.drawImage(image, x, y, radius * 2, radius * 2);
    }

    @Override
    public boolean takeHit() {
        return x > WINDOW_HEIGHT && y > WINDOW_HEIGHT;
    }
}