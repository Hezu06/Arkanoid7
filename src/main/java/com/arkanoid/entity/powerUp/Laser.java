package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.Paddle;
import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Laser extends PowerUp {
    private static final int DURATION_MS = 5000; // 5 giây

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

        // Tự động bắn liên tục trong 5 giây
        new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < DURATION_MS) {
                // Tạo 2 tia laser bắn ra từ hai bên paddle
                double leftX = paddle.getX() + 10;
                double rightX = paddle.getX() + paddle.getWidth() - 14;
                double y = paddle.getY() - 15;

                synchronized (game.getLaserBeams()) {
                    game.getLaserBeams().add(new LaserBeam(leftX, y));
                    game.getLaserBeams().add(new LaserBeam(rightX, y));
                }

                try {
                    Thread.sleep(250); // mỗi 0.25s bắn một lần
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            paddle.setLaserPowerUpInEffect(false);
            System.out.println("Laser PowerUp ended!");
        }).start();

        deactivate();
    }
}
