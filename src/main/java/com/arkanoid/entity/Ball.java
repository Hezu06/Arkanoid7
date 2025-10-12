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

    public Ball(double x, double y, double dx, double dy, double speed, double radius) {
        super(x, y, (int) (2 * radius), (int) (2 * radius), dx, dy);
        this.speed = speed;
        this.radius = radius;
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            this.dx /= length;
            this.dy /= length;
        }
        numberOfBalls++;
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Ball/tennisball.png")));
        hitSound = new AudioClip(
                Objects.requireNonNull(getClass().getResource("/sounds/collision_sound.wav")).toExternalForm()
        );
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
            hitSound.play();
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
        int steps = (int) Math.ceil(speed * deltaTime / radius); // số bước nhỏ
        double stepTime = deltaTime / steps;
        x += dx * speed * stepTime;
        y += dy * speed * stepTime;
        if (x <= 0) {
            x = 0;
            dx = Math.abs(dx); // Đảo hướng sang phải
            hitSound.play();
        }

        // Va chạm với tường phải
        if (x + radius * 2 >= WINDOW_WIDTH) {
            x = WINDOW_WIDTH - radius * 2;
            dx = -Math.abs(dx); // Đảo hướng sang trái
            hitSound.play();
        }

        // Va chạm với tường trên
        if (y <= 0) {
            y = 0;
            dy = Math.abs(dy); // Đảo hướng xuống dưới
            hitSound.play();
        }

        if (y > WINDOW_HEIGHT) {
            //System.out.println("Ball fell below the screen!");
            alive = false;
            if (numberOfBalls < 1) {
                playAgain = true;
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