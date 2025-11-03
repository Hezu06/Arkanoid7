package com.arkanoid.game;

import com.arkanoid.entity.Ball;

import com.arkanoid.entity.GameObject;
import com.arkanoid.entity.brick.*;
import com.arkanoid.entity.powerUp.PowerUp;
import com.arkanoid.entity.powerUp.PowerUpFactory;
import com.arkanoid.level.Level;
import com.arkanoid.level.LevelLoader;
import com.arkanoid.ui.ScoreScreen;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.arkanoid.entity.Paddle;
import com.arkanoid.entity.powerUp.LaserBeam;
import javafx.scene.shape.Rectangle;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;

import javafx.scene.image.Image;



import com.arkanoid.level.DifficultySettings;
import com.arkanoid.ui.GameMenu;

public class GameMain extends Application {

    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 800;

    private GraphicsContext gc;
    private List<Brick> bricks;
    private Paddle paddle;
    private Level.LevelDifficulty levelDifficulty;
    private final List<ExplosionEffect> activeExplosion = new ArrayList<>();
    private final List<Ball> listBalls = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private List<LaserBeam> laserBeams = new ArrayList<>();
    private boolean barrierActive = false;
    private long barrierStartTime;
    private final long BARRIER_DURATION = 5000; // 5 giây
    private final double BARRIER_HEIGHT = 10;

    public List<LaserBeam> getLaserBeams() {
        return laserBeams;
    }



    public static ImageView ballTexture;
    // --- Texture field ---
    private ImageView backgroundTexture;
    private ImageView paddleTexture;

    // --- Game State Manager ---
    private GameStateManager gameStateManager;
    private boolean playAgainShown = false;
    private boolean paused = false;
    private Pane gamePane;
    private final Stage primaryStage;

    // --- Constructor GameMain  ---
    public GameMain(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // --- Getter gamePane  ---
    public Pane getGamePane() {
        return gamePane;
    }

    // --- Getter/Setter Paused  ---
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // --- Getter backgroundTexture  ---
    public ImageView getBackgroundTexture() {
        return backgroundTexture;
    }

    // --- Getter ballTexture  ---
    public ImageView getBallTexture() {
        return ballTexture;
    }

    // --- Getter paddleTexture  ---
    public ImageView getPaddleTexture() {
        return paddleTexture;
    }

    // --- Launch State ---
    private boolean isBallReadyToLaunch = true;

    // --- Setter field ---
    public void setBallTexture(ImageView ballTexture) {
        GameMain.ballTexture = ballTexture;
    }

    public void setPaddleTexture(ImageView paddleTexture) {
        this.paddleTexture = paddleTexture;
    }

    public void setBackgroundTexture(ImageView backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public void setLevelDifficulty(Level.LevelDifficulty levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }

    @Override
    public void start(Stage primaryStage) {

        gamePane = new Pane();
        Scene scene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT, Color.CYAN);

        // --- 1. Canvas Setup ---
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        GameMenu.Transition(backgroundTexture);
        gamePane.getChildren().addAll(backgroundTexture, canvas);

        // --- 2. Initialize Game State and Level ---
        bricks = loadLevel();

        listBalls.add(new Ball(400, 400, 0, -1, DifficultySettings.getBallSpeed(levelDifficulty), 15, ballTexture.getImage()));
        paddle = new Paddle(350, 775, DifficultySettings.getPaddleWidth(levelDifficulty), 600, paddleTexture.getImage());

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.setMovingLeft(true);
                case RIGHT -> paddle.setMovingRight(true);

                // Launch the ball on SPACE/UP
                case SPACE, UP -> {
                    if (isBallReadyToLaunch && !listBalls.isEmpty()) {
                        isBallReadyToLaunch = false;
                    }
                }
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
                if (paused) return;
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                    // Convert nanosecond to second.
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


    private List<Brick> loadLevel() {
        LevelLoader loader = new LevelLoader();
        String fileName;
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
            return List.of();
        }
    }

    /**
     * Resets ball and paddle to initial position after losing a life.
     * Sets the state to wait for user launch input.
     */
    private void resetBallAndPaddle() {
        powerUps.clear();
        listBalls.clear();

        // Set the state to wait for user's input.
        this.isBallReadyToLaunch = true;

        paddle = new Paddle(350, 775, DifficultySettings.getPaddleWidth(levelDifficulty), 600, paddleTexture.getImage());
        paddle.setMovingLeft(false);
        paddle.setMovingRight(false);
        // Initialise a new ball.
        listBalls.add(new Ball(
                400, 400, 0, -1,
                DifficultySettings.getBallSpeed(levelDifficulty), 15,
                ballTexture.getImage()));

        System.out.println("Life Lost. Resetting Ball and Paddle. Lives remaining: " + GameStateManager.getInstance().getLives());
    }

    /**
     * Explosion Handler.
     * @param affectedCoords Coords that are affected by the Explosive Brick
     * @param bricksToRemove The bricks affected are added to the list.
     */
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

                    GameStateManager.getInstance().addScoreForNormalBrick();

                    activeBrick.setBroken(true);
                    activeBrick.setFading(true);
                    bricksToRemove.add(activeBrick);
                    break;
                }
            }
        }
    }


    /**
     * Helper function to handle brick broken.
     *
     * @param brick         brick that has been hit
     * @param bricksToRemove    brick that are eligible to be removed
     */
    private void handleBrickBreak(Brick brick, List<Brick> bricksToRemove) {
        // Increase score
        if (brick instanceof StrongBrick) {
            gameStateManager.addScoreForStrongBrick();
        }
        else {
            gameStateManager.addScoreForNormalBrick();
        }

        // Power-ups drop
        PowerUp newPowerUp = PowerUpFactory.createPowerUp(
                brick.getX(), brick.getY(),
                levelDifficulty
        );
        if  (newPowerUp != null) {
            powerUps.add(newPowerUp);
        }

        // Explosion effect
        activeExplosion.add(new ExplosionEffect(
                brick.getX(), brick.getY(),
                brick.getWidth(), brick.getHeight(),
                brick
        ));

        // Explosive Brick
        if (brick instanceof ExplosiveBrick) {
            List<int[]> affectedCoords = brick.triggerSpecialAction();
            if (!affectedCoords.isEmpty()) {
                handleExplosion(affectedCoords, bricksToRemove);
            }
        }
    }

    private boolean isLevelComplete() {
        if (bricks == null || bricks.isEmpty()) {
            return true;
        }

        // Check if all remaining bricks are Unbreakable Bricks.
        for (Brick brick : bricks) {
            if (!(brick instanceof UnbreakableBrick)) {
                return false;
            }
        }

        return true;
    }

    private void update(double deltaTime) {
        for (ExplosionEffect effect : activeExplosion) {
            effect.update(deltaTime);
        }

        for (Brick brick : bricks) {
            brick.update();
        }

        paddle.update(deltaTime);

        Iterator<LaserBeam> it = laserBeams.iterator();
        while (it.hasNext()) {
            LaserBeam beam = it.next();
            beam.update();

            // Kiểm tra va chạm với gạch
            for (Brick brick : bricks) {
                if (beam.collidesWith(brick)) {
                    brick.takeHit();
                    it.remove();
                    break;
                }
            }

            // Xóa laser nếu bay ra ngoài màn
            if (beam.getY() < 0) {
                it.remove();
            }
        }


        // Remove all explosions that have finished their animation.
        activeExplosion.removeIf(ExplosionEffect::isFinished);

        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }

        Iterator<Ball> iterator = listBalls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            if (!ball.isAlive()) {
                iterator.remove();

                // --- Life Management ---
                if (listBalls.isEmpty()) {
                    GameStateManager.getInstance().decreaseLives();   // Life lost.
                    if (!GameStateManager.getInstance().isGameOver()) {
                        resetBallAndPaddle();
                        paddle.setLaserInterrupted(true);
                        paddle.setLaserPowerupInEffect(false);
                        break;
                    }
                    else {
                        // Game Over, show Play Again.
                        if (!playAgainShown) {
                            showPlayAgain();
                            playAgainShown = true;
                        }
                    }
                    break;
                }
                continue;
            }
            // If ball is ready to launch, place it at the paddle center.
            if (isBallReadyToLaunch) {
                ball.setX(paddle.getX() + (paddle.getWidth() / 2) - (ball.getWidth() / 2));
                ball.setY(paddle.getY() - ball.getHeight());
                // Subtract 1 pixel to prevent ball - paddle collision.
            }
            else {
                ball.move(deltaTime);
            }
        }

        List<Brick> bricksToRemove = new ArrayList<>();

        for (Brick brick : bricks) {

            // Chỉ kiểm tra va chạm nếu gạch chưa bị vỡ
            if (brick.isBroken()) {
                continue;
            }

            for (Ball ball : listBalls) {
                if (ball.isFireBall()) {
                    if (ball.checkCollision(brick)) {
                        if (brick instanceof UnbreakableBrick) {
                            ball.bounceOff(brick);
                            break;
                        } else {
                            if (brick.takeHit()) {
                                handleBrickBreak(brick, bricksToRemove);
                            }
                        }
                    }
                } else {
                    if (ball.checkCollision(brick)) {
                        ball.bounceOff(brick);

                        if (brick.takeHit()) {
                            handleBrickBreak(brick, bricksToRemove);
                        }
                    }
                }
            }
        }
        this.bricks.removeAll(bricksToRemove);

        // --- Level Completion Check (Win) ---
        if (isLevelComplete() && !playAgainShown) {
            System.out.println("Level Complete!");
            showPlayAgain();
            playAgainShown = true;
            return; // Stop processing and rendering once game is paused.
        }

        // Ball - Paddle Collision.
        for (Ball ball : listBalls) {
            if (ball.checkCollision(paddle)) {
                if (!isBallReadyToLaunch) {
                    ball.bounceOff(paddle);
                }
            }
        }

        // Ball - Immortal Barrier Collision (nếu đang có)
        // === HANDLE BARRIER (IMMORTAL POWERUP) ===

// Kiểm tra thời gian tồn tại của barrier
        if (barrierActive && System.currentTimeMillis() - barrierStartTime > BARRIER_DURATION) {
            barrierActive = false;
            System.out.println("Barrier deactivated!");
        }

// Kiểm tra va chạm bóng với barrier
        if (barrierActive) {
            for (Ball ball : listBalls) {
                double bottom = ball.getY() + ball.getRadius();
                if (bottom >= WINDOW_HEIGHT - BARRIER_HEIGHT) {
                    ball.reverseY();
                    ball.setY(WINDOW_HEIGHT - BARRIER_HEIGHT - ball.getRadius());
                }
            }
        }


        // Power-up Interaction.
        for(PowerUp powerUp : powerUps) {
            if (powerUp.isActive() && powerUp.checkCollision(paddle)) {
                powerUp.takeHit();
                powerUp.applyEffect(this);
            }
        }

        this.bricks.removeAll(bricksToRemove);

        // Xóa các gạch đã hoàn thành hiệu ứng mờ dần (sửa lỗi CME Vị trí 2)
        this.bricks.removeIf(b -> b.isBroken() && b.getOpacity() <= 0);

        // Xóa các power-up đã được "ăn" hoặc bay ra khỏi màn hình
        this.powerUps.removeIf(p -> !p.isActive() || p.getY() > WINDOW_HEIGHT);

        // Xóa các hiệu ứng nổ đã kết thúc
        activeExplosion.removeIf(ExplosionEffect::isFinished);
    }

    private void render() {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // --- Lives and Scores UI ---
        GameStateManager.getInstance().render(gc, WINDOW_WIDTH, WINDOW_HEIGHT);

        // --- Rendering entities ---

        for (Brick brick : bricks) {
            brick.render(gc);
        }

        for (ExplosionEffect effect : activeExplosion) {
            effect.render(gc);
        }

        for (Ball ball : listBalls) {
            ball.render(gc);
        }

        for (LaserBeam laser : laserBeams) {
            laser.render(gc);
        }

        paddle.render(gc);

        for (PowerUp powerUp : powerUps) {
            powerUp.render(gc);
        }

        if (barrierActive) {
            gc.setFill(Color.GOLD);
            gc.fillRect(0, WINDOW_HEIGHT - BARRIER_HEIGHT, WINDOW_WIDTH, BARRIER_HEIGHT);
        }
    }

    public void resetGame() {
        System.out.println("Resetting Game");
        playAgainShown = false;

        // Reset Game State if this is Game Over / Level Completion.


        Ball.setNumberOfBalls(0);

        playAgainShown = false;

        bricks = loadLevel();
        listBalls.clear();

        // Set the state for launching a new ball.
        isBallReadyToLaunch = true;
        listBalls.add(new Ball(400, 400, 0, -1,
                DifficultySettings.getBallSpeed(levelDifficulty), 15,
                ballTexture.getImage()));
        paddle = new Paddle(350, 775,
                DifficultySettings.getPaddleWidth(levelDifficulty), 600,
                paddleTexture.getImage());

        powerUps = new ArrayList<>();

        // Re-add the canvas.
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // BUG-FIX: Background blank after press PLAY AGAIN btn.
        gamePane.getChildren().clear();
        GameMenu.Transition(backgroundTexture);
        gamePane.getChildren().addAll(backgroundTexture, canvas);
    }

    public void activateBarrier() {
        if (!barrierActive) {
            barrierActive = true;
            barrierStartTime = System.currentTimeMillis();
            System.out.println("Barrier activated!");
        }
    }


    private void showPlayAgain() {
        paused = true;
        ScoreScreen scoreScreen = new ScoreScreen(primaryStage, GameStateManager.getInstance().getScore(), this);

        scoreScreen.show();
    }


    public Paddle getPaddle() {
        return paddle;
    }

    public void fireLasersFromPaddle() {
        double paddleY = paddle.getY();
        double leftX = paddle.getX() + 10;
        double rightX = paddle.getX() + paddle.getWidth() - 10;

        laserBeams.add(new LaserBeam(leftX, paddleY));
        laserBeams.add(new LaserBeam(rightX, paddleY));
    }


    public void spawnExtraBalls() {
        listBalls.add(new Ball(
                listBalls.getFirst().getX(), listBalls.getFirst().getY(),
                3, -1,
                DifficultySettings.getBallSpeed(levelDifficulty), 15,
                ballTexture.getImage()));
        listBalls.add(new Ball(
                listBalls.getFirst().getX(), listBalls.getFirst().getY(),
                1, -1,
                DifficultySettings.getBallSpeed(levelDifficulty), 15,
                ballTexture.getImage()));
    }

    public void spawnLaserBeams() {
        Paddle paddle = getPaddle();
        if (!paddle.isLaserActive()) return;

        // Vị trí hai đầu paddle để bắn ra 2 tia laser
        double leftX = paddle.getX() + 10;
        double rightX = paddle.getX() + paddle.getWidth() - 10;
        double y = paddle.getY() - 10;

        laserBeams.add(new LaserBeam(leftX, y));
        laserBeams.add(new LaserBeam(rightX, y));

        System.out.println("Laser beams fired!");
    }

    private Canvas canvas;
    public Canvas getCanvas() {
        return canvas;
    }

    public List<Ball> getListBalls() {
        return listBalls;
    }

    public GameStateManager getGameStateManager() {
        return GameStateManager.getInstance();
    }

    public void deactivateBarrier() {
    }
}