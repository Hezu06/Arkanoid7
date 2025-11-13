package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.Paddle;
import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Laser extends PowerUp {

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

        paddle.activateLaserPowerUp(game);

        System.out.println("Laser PowerUp activated!");

        // Tự hủy (viên power-up biến mất sau khi nhặt)
        deactivate();
    }
}