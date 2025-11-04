package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.Paddle;
import com.arkanoid.game.GameMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;

public class Laser extends PowerUp {
    private static final int DURATION_MS = 5000; // 5 giây
    private static final int SHOOT_INTERVAL_MS = 250;
    private Timeline laserTimeline;

    public Laser(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/Laser.png"))));
    }

    @Override
    protected Color getColor() {
        return Color.RED;
    }

    @Override
    public void applyEffect(GameMain game) {
        Paddle paddle = game.getPaddle();

        paddle.setLaserPowerUpInEffect(true);
        paddle.setLaserInterrupted(false);
        System.out.println("Laser PowerUp activated!");

        laserTimeline = new Timeline(new KeyFrame(Duration.millis(SHOOT_INTERVAL_MS), e -> {
            // Nếu game bị reset hoặc paddle không còn tồn tại thì dừng
            if (!paddle.isLaserPowerUpInEffect()) {
                stopLaser(paddle);
                return;
            }

            // Tạo 2 tia laser bắn ra từ hai bên paddle
            double leftX = paddle.getX() + 10;
            double rightX = paddle.getX() + paddle.getWidth() - 14;
            double y = paddle.getY() - 15;

            game.getLaserBeams().add(new LaserBeam(leftX, y));
            game.getLaserBeams().add(new LaserBeam(rightX, y));
        }));

        // Số lần bắn = tổng thời gian / thời gian mỗi lần
        int repeatCount = DURATION_MS / SHOOT_INTERVAL_MS;
        laserTimeline.setCycleCount(repeatCount);

        // Khi timeline kết thúc thì tắt laser
        laserTimeline.setOnFinished(e -> stopLaser(paddle));

        laserTimeline.play();
        deactivate();
    }

    private void stopLaser(Paddle paddle) {
        if (laserTimeline != null) {
            laserTimeline.stop();
        }
        paddle.setLaserPowerUpInEffect(false);
        System.out.println("Laser PowerUp ended!");
    }

    public void stopImmediately() {
        // Hàm này có thể gọi khi reset game
        if (laserTimeline != null) {
            laserTimeline.stop();
        }
    }
}
