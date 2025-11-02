package com.arkanoid.entity.powerUp;
import com.arkanoid.level.Level;
import java.util.Random;

import com.arkanoid.level.DifficultySettings;

public class PowerUpFactory {
    public enum PowerUpType {
        EXPAND_PADDLE,
        FIRE_BALL,
        IMMORTAL,
        MULTI_BALL,
        EXTRA_LIVES,
        EXTRA_COINS
    }

    public static final Random random = new Random();

    public static PowerUp createPowerUp(double x, double y, Level.LevelDifficulty levelDifficulty) {

        double roll = random.nextDouble();
        if (roll < DifficultySettings.getPowerUpChance(levelDifficulty)) {
            // Update later when all PowerUps are done.
            PowerUpType[] dropTypes = {
                    PowerUpType.EXPAND_PADDLE,
                    PowerUpType.MULTI_BALL,
                    PowerUpType.EXTRA_LIVES,
                    PowerUpType.EXTRA_COINS
            };

            // Randomly select one power-up.
            PowerUpType type = dropTypes[random.nextInt(dropTypes.length)];

            return switch (type) {
                case EXPAND_PADDLE -> new ExpandPaddle(x, y);
                case MULTI_BALL -> new MultiBall(x, y);
                case EXTRA_LIVES -> new ExtraLives(x, y);
                case EXTRA_COINS ->  new ExtraCoins(x, y);
                case FIRE_BALL, IMMORTAL -> null;
            };
        }
        return null;
    }
}
