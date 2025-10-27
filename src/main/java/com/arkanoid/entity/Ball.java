package com.arkanoid.entity;

import com.arkanoid.entity.powerUp.PowerUp;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.util.Objects;

public class Ball extends MovableObject {
    private final double speed;
    private final double radius;
    private final Image image;
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 800;
    private final AudioClip hitSound;
    private boolean playAgain = false;
    private static int numberOfBalls = 0;



    private boolean alive = true;

    public Ball(double x, double y, double dx, double dy, double speed, double radius, Image image) {
        super(x, y, (int) (2 * radius), (int) (2 * radius), dx, dy);
        this.speed = speed;
        this.radius = radius;
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            this.dx /= length;
            this.dy /= length;
        }
        numberOfBalls++;
        this.image = image;
        hitSound = new AudioClip(
                Objects.requireNonNull(getClass().getResource("/sounds/collision_sound.wav")).toExternalForm()
        );
    }

    public void bounceOff(GameObject other) {
        Rectangle2D rect = other.getBounds();

        double cx = getCenterX();
        double cy = getCenterY();

        double closestX = Math.max(rect.getMinX(), Math.min(cx, rect.getMaxX()));
        double closestY = Math.max(rect.getMinY(), Math.min(cy, rect.getMaxY()));

        double nx = cx - closestX;
        double ny = cy - closestY;

        double distSq = nx * nx + ny * ny;
        if (distSq == 0) return; // tránh chia 0 khi tâm nằm trong vật thể

        double dist = Math.sqrt(distSq);
        nx /= dist;
        ny /= dist;

        // --- Paddle ---
        if (other instanceof Paddle paddle) {
            double hitPos = (cx - paddle.getX()) / paddle.getWidth();
            hitPos = Math.max(0, Math.min(1, hitPos));
            double angle = Math.toRadians(150 * (hitPos - 0.5)); // ±75°
            dx = Math.sin(angle);
            dy = -Math.cos(angle);
            y = paddle.getY() - radius * 2 - 1;
            hitSound.play();
            return;
        }

        // --- Phản xạ vật lý chung (Brick / Wall) ---
        // Vector phản xạ: r = d - 2(d·n)n
        double dot = dx * nx + dy * ny;
        dx -= 2 * dot * nx;
        dy -= 2 * dot * ny;

        // Đảm bảo bóng ra khỏi vật thể
        double penetration = radius - dist;
        if (penetration > 0) {
            x += nx * penetration;
            y += ny * penetration;
        }

        // Chuẩn hóa hướng để tránh lỗi trôi
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len != 0) {
            dx /= len;
            dy /= len;
        }

        hitSound.play();
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

    public void move(double deltaTime) {
        int steps = (int) Math.ceil(speed * deltaTime / radius);
        double stepTime = deltaTime / steps;

        for (int i = 0; i < steps; i++) {
            x += dx * speed * stepTime;
            y += dy * speed * stepTime;

            // kiểm tra va chạm tường trong từng bước nhỏ
            if (x <= 0) {
                x = 0;
                dx = Math.abs(dx);
                hitSound.play();
            }

            if (x + radius * 2 >= WINDOW_WIDTH) {
                x = WINDOW_WIDTH - radius * 2;
                dx = -Math.abs(dx);
                hitSound.play();
            }

            if (y <= 0) {
                y = 0;
                dy = Math.abs(dy);
                hitSound.play();
            }

            if (y > WINDOW_HEIGHT) {
                alive = false;
                if (numberOfBalls < 1) {
                    playAgain = true;
                }
                return; // bóng đã rơi, dừng lại
            }
        }
    }


    public Rectangle2D getBounds() {
        return new Rectangle2D(
                x,
                y,
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


    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(int numberOfBalls) {
        Ball.numberOfBalls = numberOfBalls;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isPlayAgain() {
        return playAgain;
    }

    public void setPlayAgain(boolean playAgain) {
        this.playAgain = playAgain;
    }

    @Override
    public boolean takeHit() {return y > WINDOW_HEIGHT;};
}