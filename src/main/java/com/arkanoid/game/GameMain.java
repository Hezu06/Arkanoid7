package com.arkanoid.game;

import com.arkanoid.entity.Ball;
import com.arkanoid.entity.brick.Brick;
import com.arkanoid.entity.brick.ExplosionEffect;
import com.arkanoid.entity.brick.ExplosiveBrick;
import com.arkanoid.entity.brick.UnbreakableBrick;
import com.arkanoid.entity.powerUp.PowerUp;
import com.arkanoid.entity.powerUp.PowerUpFactory;
import com.arkanoid.level.Level;
import com.arkanoid.level.LevelLoader;
import com.arkanoid.ui.ButtonEffects;
import com.arkanoid.ui.GameButton;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.arkanoid.entity.Paddle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.arkanoid.level.DifficultySettings;
import com.arkanoid.ui.GameMenu;

public class GameMain extends Application {

    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 800;

    private static final double PADDLE_START_Y = 775;
    private static final double BALL_RADIUS = 15;

    private GraphicsContext gc;
    private List<Brick> bricks;
    private Paddle paddle;
    private Level.LevelDifficulty levelDifficulty;
    private final List<ExplosionEffect> activeExplosion = new ArrayList<>();
    private final List<Ball> listBalls = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private ImageView backgroundTexture;

    public static ImageView ballTexture;
    private ImageView paddleTexture;

    public void setBallTexture(ImageView ballTexture) {
        GameMain.ballTexture = ballTexture;
    }

    public void setPaddleTexture(ImageView paddleTexture) {
        this.paddleTexture = paddleTexture;
    }

    public void setBackgroundTexture(ImageView backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }
    private boolean playAgainShown = false;
    private boolean paused = false;
    private Pane gamePane;

    public void setLevelDifficulty(Level.LevelDifficulty levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }
    private static final String BACKGROUND_PATH = "/assets/Background/galaxyBackground.jpg";

    @Override
    public void start(Stage primaryStage) {

        gamePane = new Pane();
        Scene scene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT, Color.CYAN);

        // --- 1. Canvas Setup ---
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Get the single drawing tool (GraphicsContext)
        gc = canvas.getGraphicsContext2D();
        GameMenu.Transition(backgroundTexture);
        gamePane.getChildren().addAll(backgroundTexture, canvas);
        // --- 2. Load the Level ---
        bricks = loadLevel();

        int paddleWidth = DifficultySettings.getPaddleWidth(levelDifficulty);
        // Tự động căn giữa paddle
        double paddleX = (double) (WINDOW_WIDTH - paddleWidth) / 2;
        paddle = new Paddle(paddleX, PADDLE_START_Y, paddleWidth, 600, paddleTexture.getImage());

        // 2. Tạo Bóng (dựa trên vị trí paddle)
        // Căn giữa bóng theo chiều X của paddle
        double ballX = paddle.getX() + (paddle.getWidth() / 2) - BALL_RADIUS;
        // Đặt bóng ngay trên mặt paddle
        double ballY = paddle.getY() - (BALL_RADIUS * 2) - 1;

        listBalls.add(new Ball(ballX, ballY, 0, -1, // Hướng đi lên
                DifficultySettings.getBallSpeed(levelDifficulty), BALL_RADIUS, ballTexture.getImage()));

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
                if (paused) return;
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1_000_000_000.0;// đổi ns → giây
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

        for (Brick brick : bricks) {
            brick.update();
        }
        paddle.update(deltaTime);
        // Remove all explosions that have finished their animation.
        activeExplosion.removeIf(ExplosionEffect::isFinished);

        for (Ball ball : listBalls) {
            ball.move(deltaTime);
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }

        List<Brick> bricksToRemove = new ArrayList<>();

        for (Brick brick : bricks) {
            // Chỉ kiểm tra va chạm nếu gạch chưa bị vỡ
            if (brick.isBroken()) {
                continue;
            }

            for (Ball ball : listBalls) {
                if (ball.checkCollision(brick)) {
                    if (ball.isFireBall()) {
                        if (brick instanceof UnbreakableBrick) {
                            ball.bounceOff(brick);
                            break;
                        } else {
                            if (brick.takeHit()) {
                                PowerUp newPowerUp = PowerUpFactory.createPowerUp(brick.getX(), brick.getY(), levelDifficulty);
                                if (newPowerUp != null) {
                                    powerUps.add(newPowerUp);
                                }

                                activeExplosion.add(new ExplosionEffect(
                                        brick.getX(), brick.getY(),
                                        brick.getWidth(), brick.getHeight(),
                                        brick
                                ));
                                if (brick instanceof ExplosiveBrick) {
                                    List<int[]> affectedCoords = brick.triggerSpecialAction();
                                    if (!affectedCoords.isEmpty()) {
                                        handleExplosion(affectedCoords, bricksToRemove);
                                    }
                                }
                                bricksToRemove.add(brick);
                            }
                        }
                    } else {
                        ball.bounceOff(brick);
                        if (brick.takeHit()) {
                            if (!(brick instanceof UnbreakableBrick)) {
                                PowerUp newPowerUp = PowerUpFactory.createPowerUp(brick.getX(), brick.getY(), levelDifficulty);
                                if (newPowerUp != null) {
                                    powerUps.add(newPowerUp);
                                }

                                activeExplosion.add(new ExplosionEffect(
                                        brick.getX(), brick.getY(),
                                        brick.getWidth(), brick.getHeight(),
                                        brick
                                ));
                            }
                            if (brick instanceof ExplosiveBrick) {
                                List<int[]> affectedCoords = brick.triggerSpecialAction();
                                if (!affectedCoords.isEmpty()) {
                                    handleExplosion(affectedCoords, bricksToRemove);
                                }
                            }
                            bricksToRemove.add(brick);
                        }
                    }
                }
            }
        }

        this.bricks.removeAll(bricksToRemove);


        // Va chạm bóng - thanh
        for (Ball ball : listBalls) {
            if (ball.checkCollision(paddle)) {
                ball.bounceOff(paddle);
            }
        }

        for(PowerUp powerUp : powerUps) {
            if (powerUp.isActive() && powerUp.checkCollision(paddle)) {
                powerUp.takeHit();
                powerUp.applyEffect(this);
            }
        }

        this.bricks.removeAll(bricksToRemove);

        // Xóa các gạch đã hoàn thành hiệu ứng mờ dần (sửa lỗi CME Vị trí 2)
        this.bricks.removeIf(b -> b.isBroken() && b.getOpacity() <= 0);

        // Xóa các bóng đã chết (sửa lỗi CME Vị trí 1 và lỗi logic)
        this.listBalls.removeIf(ball -> !ball.isAlive());

        // Xóa các power-up đã được "ăn" hoặc bay ra khỏi màn hình
        this.powerUps.removeIf(p -> !p.isActive() || p.getY() > WINDOW_HEIGHT);

        // Xóa các hiệu ứng nổ đã kết thúc
        activeExplosion.removeIf(ExplosionEffect::isFinished);

        if (listBalls.isEmpty() && Ball.getNumberOfBalls() <= 0 && !playAgainShown) {
            showPlayAgainButton(); // Hàm này sẽ set paused = true
            playAgainShown = true; // Đặt cờ này ở đây
        }
    }

    private void render() {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Call the individual object's render method, passing the shared gc
        for (Brick brick : bricks) {
            brick.render(gc);
        }

        for (ExplosionEffect effect : activeExplosion) {
            effect.render(gc);
        }

        for (Ball ball : listBalls) {
            ball.render(gc);
        }

        paddle.render(gc);

        for (PowerUp powerUp : powerUps) {
            powerUp.render(gc);
        }
    }

    private void resetGame() {
        // Reset trạng thái
        System.out.println("Resetting Game");

        listBalls.clear();
        powerUps.clear();

        Ball.setNumberOfBalls(0);

        playAgainShown = false;

        bricks = loadLevel();

        int paddleWidth = DifficultySettings.getPaddleWidth(levelDifficulty);
        double paddleX = (double) (WINDOW_WIDTH - paddleWidth) / 2; // Căn giữa
        paddle = new Paddle(paddleX, PADDLE_START_Y, paddleWidth, 600, paddleTexture.getImage());

        // 2. Tạo Bóng (dựa trên vị trí paddle)
        double ballX = paddle.getX() + (paddle.getWidth() / 2) - BALL_RADIUS;
        double ballY = paddle.getY() - (BALL_RADIUS * 2) - 1; // Ngay trên paddle

        listBalls.add(new Ball(ballX, ballY, 0, -1,
                DifficultySettings.getBallSpeed(levelDifficulty), BALL_RADIUS, ballTexture.getImage()));
        gamePane.getChildren().clear();
        // Thêm lại canvas
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gamePane.getChildren().addAll(backgroundTexture, canvas);
    }

    private void showPlayAgainButton() {
        paused = true;
        GameButton playAgainBtn = new GameButton("PLAY AGAIN");

        playAgainBtn.setFont(Font.loadFont(
                getClass().getResourceAsStream("/fonts/ALIEN5.TTF"), 36
        ));
        ButtonEffects.applyHoverEffect(playAgainBtn);
        VBox box = new VBox(playAgainBtn);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        gamePane.getChildren().add(box);

        playAgainBtn.setOnAction(e -> {
            gamePane.getChildren().clear(); // Xóa toàn bộ
            paused = false;
            resetGame(); // Khởi tạo lại
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void spawnExtraBalls() {
        listBalls.add(new Ball(listBalls.getFirst().getX(), listBalls.getFirst().getY(),
                3, -1, DifficultySettings.getBallSpeed(levelDifficulty), 15,  ballTexture.getImage()));
        listBalls.add(new Ball(listBalls.getFirst().getX(), listBalls.getFirst().getY(),
                1, -1, DifficultySettings.getBallSpeed(levelDifficulty), 15,  ballTexture.getImage()));
    }

    public List<Ball> getListBalls() {
        return listBalls;
    }
}