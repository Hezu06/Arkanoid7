package com.arkanoid.game;

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

import java.util.List;

public class GameMain extends Application {

    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 900;

    // GraphicsContext must be a field to be accessible within the AnimationTimer
    private GraphicsContext gc;
    private List<Brick> bricks;

    @Override
    public void start(Stage primaryStage) {
        Pane gamePane = new Pane();
        Scene scene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT);

        // 1. Create the Canvas (the drawing surface)
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gamePane.getChildren().add(canvas);

        // 2. Get the GraphicsContext (the drawing tool)
        gc = canvas.getGraphicsContext2D();

        // 3. Load the bricks list
        bricks = loadBricks();

        System.out.println("Final Bricks Loaded into App: " + (bricks != null ? bricks.size() : "NULL"));

        // 4. Start the main game loop (renderer)
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Clear the screen (always required when using render(gc))
//                gc.setFill(Color.CYAN);
                gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

                // Call render(gc) on every active brick
                for (Brick brick : bricks) {
                    // brick.render(gc) is void, it draws directly to the canvas
                    brick.render(gc);
                }
            }
        }.start();

        primaryStage.setTitle("Arkanoid Brick Renderer Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<Brick> loadBricks() {
        LevelLoader loader = new LevelLoader();
        try {
            // Load the level file using the defined EASY difficulty
            Level level = loader.loadLevel("Hard.txt", Level.LevelDifficulty.HARD);
            System.out.println("LevelLoader internal check: Loaded " + level.getBricks().size() + " bricks from level1.txt.");
            return level.getBricks();
        } catch (java.io.FileNotFoundException e) {
            System.err.println("CRITICAL ERROR: Level file not found. Ensure 'level1.txt' is in resources/levels/");
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}