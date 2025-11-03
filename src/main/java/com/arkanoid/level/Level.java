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
}