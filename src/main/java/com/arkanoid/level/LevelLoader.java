package com.arkanoid.level;

import com.arkanoid.entity.*;
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
    private static final double BRICK_START_X = 50;
    private static final double BRICK_START_Y = 50;
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

        try (InputStream inputStream = getClass().getResourceAsStream("/levels/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int gridY = 0;

             if (inputStream == null) {
                throw new java.io.FileNotFoundException("Level file not found: /levels/" + fileName);
            }

             while ((line = bufferedReader.readLine()) != null) {
                 for (int gridX = 0; gridX < line.length(); gridX++) {
                     char code = line.charAt(gridX);
                     Brick newBrick = createBrickFromCode(code, gridX, gridY);

                     if (newBrick != null) {
                         // Position based on grid coordinates
                         double posX = BRICK_START_X + gridX * BRICK_WIDTH;
                         double posY = BRICK_START_Y + gridY * BRICK_HEIGHT;
                         newBrick.setX(posX);
                         newBrick.setY(posY);

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

    private Brick createBrickFromCode(char code, int gridX, int gridY) {
        return switch (code) {
            case 'N' -> new NormalBrick(0, 0, BRICK_WIDTH, BRICK_HEIGHT, gridX , gridY);
            case 'S' -> new StrongBrick(0, 0, BRICK_WIDTH, BRICK_HEIGHT, gridX, gridY);
            case 'X' -> new ExplosiveBrick(0, 0, BRICK_WIDTH, BRICK_HEIGHT, gridX, gridY);
            case 'U' -> new UnbreakableBrick(0, 0, BRICK_WIDTH, BRICK_HEIGHT, gridX, gridY);
            case '-' -> null; // Empty space
            default -> {
                System.err.println("Unknown brick code: " + code);
                yield null;
            }
        };
    }
}
