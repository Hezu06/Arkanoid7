package com.arkanoid.game;

import com.arkanoid.entity.Ball;
import com.arkanoid.entity.brick.Brick;
import com.arkanoid.level.Level;
import com.arkanoid.level.LevelLoader;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.arkanoid.entity.Paddle;

import java.util.List;

public class GameMain extends Application {

    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 800;

    private GraphicsContext gc;
    private List<Brick> bricks;
    private Ball ball;
    private Paddle paddle;

    @Override
    public void start(Stage primaryStage) {

        Pane gamePane = new Pane();
        Scene scene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT, Color.CYAN);

        // --- 1. Canvas Setup ---
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Get the single drawing tool (GraphicsContext)
        gc = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        // --- 2. Load the Level ---
        bricks = loadLevel();
        ball = new Ball(400, 400, 0, -1, 10, 15);
        paddle = new Paddle(350, 750, "large", 10);

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
            @Override
            public void handle(long now) {
                // RENDER CYCLE: Calls the render method every frame
                render();
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
        try {
            Level level = loader.loadLevel("Hard.txt", Level.LevelDifficulty.HARD);
            List<Brick> loadedBricks = level.getBricks();
            System.out.println("Final Bricks Loaded into App: " + loadedBricks.size());
            return loadedBricks;
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to load level.");
            e.printStackTrace();
            return List.of();
        }
    }

    private void render() {
        // Clear the screen
        gc.setFill(Color.LIGHTYELLOW);
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Call the individual object's render method, passing the shared gc
        for (Brick brick : bricks) {
            brick.render(gc);
        }

        ball.render(gc);
        ball.move();
        for (Brick brick : bricks) {
            if (ball.checkCollision(brick)) {
                ball.bounceOff(brick);
                if (brick.takeHit()) {
                    bricks.remove(brick);
                }
            }
        }
        paddle.update();
        paddle.render(gc);
        if (ball.checkCollision(paddle)) {
            ball.bounceOff(paddle);
        }
        // If you had a Paddle 'p', you would call p.render(gc) here too
    }

    public static void main(String[] args) {
        launch(args);
    }
}