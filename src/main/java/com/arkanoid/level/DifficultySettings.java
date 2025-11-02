package com.arkanoid.level;

public class DifficultySettings {
    // Constants for ballSpeed, paddleWidth and powerUpChance
    private static final double SPEED_HARD = 450;
    private static final double SPEED_VERY_HARD = 600;
    private static final double SPEED_ASIAN = 800;

    private static final int WIDTH_HARD = 150;
    private static final int WIDTH_VERY_HARD = 120;
    private static final int WIDTH_ASIAN = 100;

    private static final double CHANCE_HARD = 0.5;
    private static final double CHANCE_VERY_HARD = 0.3;
    private static final double CHANCE_ASIAN = 0.2;

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

    public static int getPaddleWidth(Level.LevelDifficulty difficulty) {
        return switch (difficulty) {
            case HARD -> WIDTH_HARD;
            case VERY_HARD -> WIDTH_VERY_HARD;
            case ASIAN -> WIDTH_ASIAN;
        };
    }
}