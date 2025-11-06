package com.arkanoid.level;

import com.arkanoid.entity.brick.Brick;
import java.util.List;

public record Level(List<Brick> bricks) {
    public enum LevelDifficulty {
        HARD,
        VERY_HARD,
        ASIAN
    }

}