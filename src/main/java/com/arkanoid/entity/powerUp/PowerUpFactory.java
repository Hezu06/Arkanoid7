package com.arkanoid.entity.powerUp;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class PowerUpFactory {
    public static final Random random = new Random();;

    public static PowerUp createPowerUp(double x, double y) {
        int roll = random.nextInt(4);

        if(roll < 2) {
            int type = random.nextInt(2);
            return switch (type) {
                case 0 -> new ExpandPaddle(x, y);
                case 1 -> new MultiBall(x, y);
                default -> null;
            };
        }
        return null;
    }
}
