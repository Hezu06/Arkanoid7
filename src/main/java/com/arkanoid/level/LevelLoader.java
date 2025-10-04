package com.arkanoid.level;

import com.arkanoid.entity.brick.*;
import com.arkanoid.entity.powerUp.PowerUp;
//import com.arkanoid.powerUp.powerUpType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelLoader {
    private static final double BRICK_WIDTH = 60;
    private static final double BRICK_HEIGHT = 20;
    private static final double BRICK_START_X = 15;
    private static final double BRICK_START_Y = 15;
    private final Random random = new Random();

    /**
     * Loads a specific level file and generates the corresponding Brick objects.
     * @param fileName The name of the level file (e.g., "level1.txt").
     * @param difficulty The difficulty setting to determine power-up chance.
     * @return A completed Level object.
     */

    public Level loadLevel(String fileName, Level.LevelDifficulty difficulty) throws Exception {
        List<Brick> bricks = new ArrayList<>();

        double powerUpChance = DifficultySettings.getPowerUpChance(difficulty);

        final String resourcePath = "/difficulty/" + fileName;

        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int gridY = 0;

            if (inputStream.available() == 0) {
                System.err.println("DEBUG: Input stream for " + fileName + " is empty (0 bytes available). Check file content.");
                return new Level(bricks, difficulty);
            }

             while ((line = bufferedReader.readLine()) != null) {
                 for (int gridX = 0; gridX < line.length(); gridX++) {
                     char code = line.charAt(gridX);

                     // Position based on grid coordinates
                     double posX = BRICK_START_X + gridX * BRICK_WIDTH;
                     double posY = BRICK_START_Y + gridY * BRICK_HEIGHT;

                     Brick newBrick = createBrickFromCode(code, gridX, gridY, posX, posY);

                     if (newBrick != null) {

                         // Assign Power-Up drop chance
                         if (!(newBrick instanceof UnbreakableBrick) && random.nextDouble() < powerUpChance) {
                             PowerUp.PowerUpType[] types = PowerUp.PowerUpType.values();
                             PowerUp.PowerUpType type = types[random.nextInt(types.length)];
                             newBrick.setPowerUpDrop(type);
                         }

                         bricks.add(newBrick);
                     }
                 }
                 gridY++;
             }
        }

        return new Level(bricks, difficulty);
    }

    private Brick createBrickFromCode(char code, int gridX, int gridY, double posX, double posY) {
        return switch (code) {
            case 'N' -> new NormalBrick(posX, posY, BRICK_WIDTH, BRICK_HEIGHT, gridX , gridY);
            case 'S' -> new StrongBrick(posX, posY, BRICK_WIDTH, BRICK_HEIGHT, gridX, gridY);
            case 'X' -> new ExplosiveBrick(posX, posY, BRICK_WIDTH, BRICK_HEIGHT, gridX, gridY);
            case 'U' -> new UnbreakableBrick(posX, posY, BRICK_WIDTH, BRICK_HEIGHT, gridX, gridY);
            case '-' -> null; // Empty space
            default -> {
                System.err.println("Unknown brick code: " + code);
                yield null;
            }
        };
    }
}
