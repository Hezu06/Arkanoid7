package com.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameStateManager {

    private int lives;
    private int score;

    // Constants for scoring
    private static final int NORMAL_BRICK_SCORE = 50;
    private static final int STRONG_BRICK_SCORE = 150;
    private static final int SCORE_PER_COIN_POWER_UP = 500;

    // Constants for lives
    private static final int MAX_LIVES = 5;
    private static final int INITIAL_LIVES = 3;

    public GameStateManager() {
        lives = INITIAL_LIVES;
        score = 0;
    }

    public int getLives() {
        return lives;
    }

    public void increaseLives() {
        if (lives < MAX_LIVES) {
            lives++;
        }
    }

    public void decreaseLives() {
        if (lives > 0) {
            lives--;
        }
    }

    public boolean isGameOver() {
        return lives == 0;
    }

    // --- SCORE MANAGEMENT ---

    public int getScore() {
        return score;
    }

    public void addScoreForNormalBrick() {
        score += NORMAL_BRICK_SCORE;
    }

    public void addScoreForStrongBrick() {
        score += STRONG_BRICK_SCORE;
    }

    public void addScoreForCoinPowerUp() {
        score += SCORE_PER_COIN_POWER_UP;
    }

    // --- UI RENDERING ---

    public void render(GraphicsContext gc, int windowWidth, int windowHeight) {
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Render Lives (TOP LEFT)
        gc.setFill(Color.WHITE);
        gc.fillText("LIVES: " + getLives(), 20, 30);

        // Render Score (TOP RIGHT)
        gc.setFill(Color.YELLOW);
        gc.fillText("Score: " + getScore(), windowWidth - 150, 30);
    }

}
