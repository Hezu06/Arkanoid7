package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.Paddle;
import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Immortal extends PowerUp {

    private static final int DURATION_MS = 5000; // 5 giây

    public Immortal(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/Immortal.png"))));
    }

    @Override
    protected Color getColor() {
        return Color.CYAN;
    }

    @Override
    public void applyEffect(GameMain game) {
        Paddle paddle = game.getPaddle();

        // Kích hoạt barrier ở GameMain
        game.activateBarrier();
        paddle.setImmortalPowerUpInEffect(true);
        System.out.println("Immortal power-up activated!");

        // Sau 5s thì tắt hiệu ứng
        new Thread(() -> {
            try {
                Thread.sleep(DURATION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            paddle.setImmortalPowerUpInEffect(false);
            game.deactivateBarrier();
            System.out.println("Immortal power-up ended!");
        }).start();

        deactivate();
    }
}