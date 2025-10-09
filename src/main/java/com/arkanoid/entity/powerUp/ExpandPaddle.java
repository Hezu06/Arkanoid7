package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.scene.paint.Color;
import com.arkanoid.entity.Paddle;

public class ExpandPaddle extends PowerUp {
    public ExpandPaddle(double x, double y) {
        super(x, y, 20, 20);
    }

    @Override
    protected Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void applyEffect(GameMain game) {
        Paddle paddle = game.getPaddle();

        if(!paddle.isExpaned()) {
            double newWidth = paddle.getWidth() * 1.5;
            paddle.setWidth((int) newWidth);
            paddle.setExpaned(true);

            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                paddle.setWidth((int) (paddle.getWidth() * 1.5));
                paddle.setExpaned(false);
            }).start();
        }
        deactivate();
    }
}
