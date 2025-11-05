package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.Paddle;
import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
// Xóa import Timeline và KeyFrame

import java.util.Objects;

public class Laser extends PowerUp {
    // Xóa các hằng số DURATION_MS, SHOOT_INTERVAL_MS
    // Xóa trường laserTimeline

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

        // Chỉ cần gọi một phương thức trên paddle
        // Chúng ta truyền 'game' vào để paddle biết nơi spawn tia laser
        paddle.activateLaserPowerUp(game);

        System.out.println("Laser PowerUp activated!");

        // Xóa tất cả code Timeline...

        // Tự hủy (viên power-up biến mất sau khi nhặt)
        deactivate();
    }

    // Xóa phương thức stopLaser()
    // Xóa phương thức stopImmediately()
}