package com.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

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

    // Configuration for heart display
    private static final double HEART_SIZE = 30;
    private static final double HEART_SPACING = 5;
    private static final String FULL_HEART_PATH = "assets/Heart/heart_alive.png";
    private static final String EMPTY_HEART_PATH = "assets/Heart/heart_dead.png";
    private final Image fullHeartImage;
    private final Image emptyHeartImage;

    public GameStateManager() {
        lives = INITIAL_LIVES;
        score = 0;
        fullHeartImage = new Image(Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(FULL_HEART_PATH)));
        emptyHeartImage = new Image(Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream(EMPTY_HEART_PATH)));
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

        // Render Score (TOP LEFT)
        gc.setFill(Color.WHITE);
        Font scoreFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"), 36
        );
        gc.setFont(scoreFont);
        gc.fillText("SCORE: " + getScore(), 10, 35);

        // Render Lives (TOP RIGHT)
        double totalHeartsWidth = (MAX_LIVES * HEART_SIZE) + ((MAX_LIVES - 1) * HEART_SPACING);
        double startX = windowWidth - totalHeartsWidth - 10;    // 10 pixels margin from the right edge
        double startY = 5;  // Top margin

        for (int i = 0; i < MAX_LIVES; i++) {
            double x = startX + (i * (HEART_SIZE + HEART_SPACING));

            // Determine which heart state to draw
            Image heartToDraw = (i < lives) ? fullHeartImage : emptyHeartImage;

            gc.drawImage(heartToDraw, x, startY, HEART_SIZE, HEART_SIZE);
        }
    }

}
