package com.arkanoid.level;

import com.arkanoid.entity.brick.Brick;
import com.arkanoid.entity.brick.ExplosiveBrick;
import com.arkanoid.entity.brick.UnbreakableBrick;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public enum LevelDifficulty {
        HARD,
        VERY_HARD,
        ASIAN
    }
    /*
    Difficulty GROWS UP:
    - INCREASE: ballSpeed, Unbreakable & Strong brick chance, brickNums.
    - DECREASE: paddleWidth, powerUpDropChance, Explosive brick chance.
    */

    private final List<Brick> bricks;
    private final LevelDifficulty difficulty;

    private final double ballSpeed;
    private final double paddleWidth;
    private final double powerUpChance;

    public Level(List<Brick> bricks, LevelDifficulty difficulty) {
        this.bricks = bricks;
        this.difficulty = difficulty;

        this.ballSpeed = DifficultySettings.getBallSpeed(this.difficulty);
        this.paddleWidth = DifficultySettings.getPaddleWidth(this.difficulty);
        this.powerUpChance = DifficultySettings.getPowerUpChance(this.difficulty);
    }

    public List<Brick> getBricks() { return bricks; }
    public LevelDifficulty getDifficulty() { return difficulty; }
    public double getBallSpeed() { return ballSpeed; }
    public double getPaddleWidth() { return paddleWidth; }
    public double getPowerUpChance() { return powerUpChance; }

    // HARDCODE MAXIMUM GRID DIMENSIONS
    private static final int MAX_GRID_COLUMNS = 12;
    private static final int MAX_GRID_ROWS = 10;

    public List<Brick> processExplosion(ExplosiveBrick centerBrick) {
        List<Brick> bricksToRemove = new ArrayList<>();
        int centerX = centerBrick.getGridX();
        int centerY = centerBrick.getGridY();

        // Handling edge case: explosiveBrick lies in the corner or on the boundary line.
        int startRow = Math.max(0, centerY - 1);
        int startCol = Math.max(0, centerX - 1);

        int endRow = Math.min(MAX_GRID_ROWS, centerY + 1);
        int endCol = Math.min(MAX_GRID_COLUMNS, centerY + 1);

        // Loop through the 3x3 grid centered at (centerX, centerY)
        for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
            for (int colIndex = startCol; colIndex <= endCol; colIndex++) {
                Brick targetToExplode = null;
                for (Brick brick : bricks) {
                    if (brick.isBroken()) {
                        continue;
                    }

                    // NO need to skip center brick here,
                    // since it is already broken and is not in the 'active' brick list anymore
                    // OR it will be ignored as the explosion is triggered AFTER it's destroyed.
                    if (brick.getGridX() == rowIndex && brick.getGridY() == colIndex) {
                        targetToExplode = brick;
                        break;
                    }
                }

                if (targetToExplode != null && !(targetToExplode instanceof UnbreakableBrick)) {
                    // Force the hit. Explosions destroys in one go.
                    targetToExplode.setCurrentDurability(1);

                    if (targetToExplode.takeHit()) {
                        bricksToRemove.add(targetToExplode);
                    }
                }
            }
        }

        centerBrick.triggerSpecialAction();
        return bricksToRemove;
    }
}