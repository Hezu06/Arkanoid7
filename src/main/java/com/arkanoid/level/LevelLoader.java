package com.arkanoid.level;

import com.arkanoid.entity.brick.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    private static final double BRICK_WIDTH = 60;
    private static final double BRICK_HEIGHT = 20;
    private static final double BRICK_START_X = 15;
    private static final double BRICK_START_Y = 15;

    /**
     * Loads a specific level file and generates the corresponding Brick objects.
     *
     * @param fileName The name of the level file (e.g., "level1.txt").
     * @return A completed Level object.
     */

    public Level loadLevel(String fileName) {
        List<Brick> bricks = new ArrayList<>();

        final String resourcePath = "/difficulty/" + fileName;

        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            assert inputStream != null;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                int gridY = 0;

                if (inputStream.available() == 0) {
                    System.err.println("DEBUG: Input stream for " + fileName + " is empty.");
                    return new Level(bricks);
                }

                 while ((line = bufferedReader.readLine()) != null) {
                     for (int gridX = 0; gridX < line.length(); gridX++) {
                         char code = line.charAt(gridX);

                         // Position based on grid coordinates
                         double posX = BRICK_START_X + gridX * BRICK_WIDTH;
                         double posY = BRICK_START_Y + gridY * BRICK_HEIGHT;

                         Brick newBrick = createBrickFromCode(code, gridX, gridY, posX, posY);

                         if (newBrick != null) {
                             bricks.add(newBrick);
                         }
                     }
                     gridY++;
                 }
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return new Level(bricks);
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
