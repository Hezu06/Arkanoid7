package com.arkanoid.game;

import com.arkanoid.entity.Ball;
import com.arkanoid.entity.brick.Brick;
import com.arkanoid.entity.brick.ExplosiveBrick;
import com.arkanoid.entity.brick.UnbreakableBrick;
import com.arkanoid.level.Level;
import com.arkanoid.level.LevelLoader;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.arkanoid.entity.Paddle;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMain extends Application {

    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 800;

    private GraphicsContext gc;
    private List<Brick> bricks;
    private Ball ball;
    private Paddle paddle;
    private Level.LevelDifficulty levelDifficulty;
    private List<ExplosionEffect> activeExplosion = new ArrayList<>();
    private Image backgroundTexture;

    public void setLevelDifficulty(Level.LevelDifficulty levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }
    private static final String BACKGROUND_PATH = "/assets/Background/galaxyBackground.jpg";

    @Override
    public void start(Stage primaryStage) {

        Pane gamePane = new Pane();
        Scene scene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT, Color.CYAN);

        // --- 1. Canvas Setup ---
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Get the single drawing tool (GraphicsContext)
        gc = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);
        backgroundTexture = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_PATH)),
                WINDOW_WIDTH, WINDOW_HEIGHT, false, true
        );
        // --- 2. Load the Level ---
        bricks = loadLevel();
        ball = new Ball(400, 400, 0, -1, 600, 15);
        paddle = new Paddle(350, 760, "small", 600);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.setMovingLeft(true);
                case RIGHT -> paddle.setMovingRight(true);
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.setMovingLeft(false);
                case RIGHT -> paddle.setMovingRight(false);
            }
        });
        // --- 3. Start the Game Loop ---
        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1_000_000_000.0; // đổi ns → giây
                    update(deltaTime);
                    render();
                }
                lastUpdate = now;
            }
        }.start();

        // --- 4. Stage Setup ---
        primaryStage.setTitle("Arkanoid Renderer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ... (loadLevel and main method omitted for brevity, assume they are correct)

    private List<Brick> loadLevel() {
        LevelLoader loader = new LevelLoader();
        String fileName = "";
        if (levelDifficulty == Level.LevelDifficulty.HARD) {
            fileName = "Hard.txt";
        } else if (levelDifficulty == Level.LevelDifficulty.VERY_HARD) {
            fileName = "VeryHard.txt";
        } else fileName = "Asian.txt";
        System.out.println(fileName);
        try {
            Level level = loader.loadLevel(fileName, levelDifficulty);
            List<Brick> loadedBricks = level.getBricks();
            System.out.println("Final Bricks Loaded into App: " + loadedBricks.size());
            return loadedBricks;
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to load level.");
            e.printStackTrace();
            return List.of();
        }
    }

    private void handleExplosion(List<int[]> affectedCoords, List<Brick> bricksToRemove) {

        for (int[] targetCoords : affectedCoords) {
            int targetX = targetCoords[0];
            int targetY = targetCoords[1];

            for (Brick activeBrick : this.bricks) {
                if (bricksToRemove.contains(activeBrick)) {
                    continue;
                }
                if (activeBrick.getGridX() == targetX && activeBrick.getGridY() == targetY) {

                    if (activeBrick instanceof UnbreakableBrick) {
                        continue;
                    }
                    activeBrick.setBroken(true);
                    activeBrick.setFading(true);
                    bricksToRemove.add(activeBrick);
                    break;
                }
            }
        }
    }

    private void update(double deltaTime) {
        for (ExplosionEffect effect : activeExplosion) {
            effect.update(deltaTime);
        }
        // Remove all explosions that have finished their animation.
        activeExplosion.removeIf(ExplosionEffect::isFinished);

        ball.move(deltaTime);

        List<Brick> bricksToRemove = new ArrayList<>();

        for (Brick brick : bricks) {
            if (ball.checkCollision(brick)) {
                ball.bounceOff(brick);
                if (brick.takeHit()) {
                    if (brick instanceof ExplosiveBrick) {
                        List<int[]> affectedCoords = brick.triggerSpecialAction();
                        if (!affectedCoords.isEmpty()) {
                            activeExplosion.add(new ExplosionEffect(
                                    brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight()
                            ));
                            handleExplosion(affectedCoords, bricksToRemove);
                        } else {
                            bricksToRemove.add(brick);
                        }
                    }
                }
            }
            bricks.removeIf(b -> b.isBroken() && !b.isFading() && b.getOpacity() <= 0);
        }
        this.bricks.removeAll(bricksToRemove);
        paddle.update(deltaTime);

        // Va chạm bóng - thanh
        if (ball.checkCollision(paddle)) {
            ball.bounceOff(paddle);
        }
    }

    private void render() {
        gc.drawImage(backgroundTexture, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Call the individual object's render method, passing the shared gc
        for (Brick brick : bricks) {
            brick.update();
            brick.render(gc);
        }

        for (ExplosionEffect effect : activeExplosion) {
            effect.render(gc);
        }

        ball.render(gc);

        paddle.render(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}