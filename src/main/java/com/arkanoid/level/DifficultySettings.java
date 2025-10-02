package com.arkanoid.level;

public class DifficultySettings {
    // Constants for ballSpeed, paddleWidth and powerUpChance
    private static final double SPEED_HARD = 300;
    private static final double SPEED_VERY_HARD = 450;
    private static final double SPEED_ASIAN = 600;

    private static final double WIDTH_HARD = 150.0;
    private static final double WIDTH_VERY_HARD = 100.0;
    private static final double WIDTH_ASIAN = 70.0;

    private static final double CHANCE_HARD = 0.35;
    private static final double CHANCE_VERY_HARD = 0.2;
    private static final double CHANCE_ASIAN = 0.1;

    public static double getPowerUpChance(Level.LevelDifficulty difficulty) {
        return switch (difficulty) {
            case HARD -> CHANCE_HARD;
            case VERY_HARD -> CHANCE_VERY_HARD;
            case ASIAN -> CHANCE_ASIAN;
        };
    }

    public static double getBallSpeed(Level.LevelDifficulty difficulty) {
        return switch (difficulty) {
            case HARD -> SPEED_HARD;
            case VERY_HARD -> SPEED_VERY_HARD;
            case ASIAN -> SPEED_ASIAN;
        };
    }

    public static double getPaddleWidth(Level.LevelDifficulty difficulty) {
        return switch (difficulty) {
            case HARD -> WIDTH_HARD;
            case VERY_HARD -> WIDTH_VERY_HARD;
            case ASIAN -> WIDTH_ASIAN;
        };
    }
}