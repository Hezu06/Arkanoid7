package com.arkanoid.entity;

import com.arkanoid.entity.powerUp.LaserBeam;
import com.arkanoid.game.GameMain;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Paddle extends MovableObject {

    private final Image paddleImage;

    private final double speed;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    private boolean expanded = false;
    private long expandEndTime = 0;
    private final double originalWidth;

    private boolean laserInterrupted = false;
    private boolean laserActive = false;
    private boolean laserPowerUpInEffect = false;

    private boolean immortalPowerUpInEffect = false;

    private static final double LASER_DURATION_SECONDS = 5.0; // 5 giây
    private static final double LASER_SHOOT_INTERVAL = 0.25; // 250ms

    private GameMain gameRef;

    private double laserTimeRemaining = 0.0;
    private double laserShootCooldown = 0.0;

    private static final int WINDOW_WIDTH = 750;

    public void activateLaserPowerUp(GameMain game) {
        this.gameRef = game;
        this.laserPowerUpInEffect = true;
        this.laserInterrupted = false;
        this.laserTimeRemaining = LASER_DURATION_SECONDS;
        this.laserShootCooldown = 0.0;
    }

    public Paddle(double x, double y, int width, double speed, Image image) {
        super(x, y, width, 20, 0, 0);
        this.speed = speed;
        this.originalWidth = width;
        this.paddleImage = image;

        removeAllPowerUpEffects();
    }

    public void update(double deltaTime) {
        move(deltaTime);

        // Hết hiệu ứng mở rộng
        if (expanded && System.currentTimeMillis() > expandEndTime) {
            width = originalWidth;
            expanded = false;
            System.out.println("Paddle shrank!");
        }


        // Hết hiệu ứng bất tử
        // thời điểm hết hiệu lực Immortal
        long immortalEndTime = 0;
        if (immortalPowerUpInEffect && System.currentTimeMillis() > immortalEndTime) {
            immortalPowerUpInEffect = false;
            System.out.println("Immortal mode ended!");
        }
        if (this.laserPowerUpInEffect) {

            // 1. Đếm ngược tổng thời gian
            this.laserTimeRemaining -= deltaTime;

            // 2. Đếm ngược thời gian giữa 2 lần bắn
            this.laserShootCooldown -= deltaTime;

            // 3. Kiểm tra nếu hết hạn hoặc bị ngắt (mất mạng)
            if (this.laserTimeRemaining <= 0 || this.laserInterrupted) {
                this.laserPowerUpInEffect = false;
                this.laserTimeRemaining = 0;
                this.laserShootCooldown = 0;
                this.gameRef = null; // Xóa tham chiếu
                System.out.println("Laser PowerUp ended!");
                return; // Dừng xử lý laser cho frame này
            }

            if (this.laserShootCooldown <= 0) {
                // Đặt lại thời gian chờ
                this.laserShootCooldown = LASER_SHOOT_INTERVAL;

                shootLasers();
            }
        }
    }

    private void shootLasers() {
        if (this.gameRef == null) {
            return;
        }

        double leftX = this.x + 10;
        double rightX = this.x + this.width - 14;
        double y = this.y - 15;

        // Dùng gameRef để thêm laser vào danh sách của GameMain
        this.gameRef.getLaserBeams().add(new LaserBeam(leftX, y));
        this.gameRef.getLaserBeams().add(new LaserBeam(rightX, y));
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getImage(), x, y, width, height);
    }

    private Image getImage() {
        return paddleImage;
    }

    @Override
    public boolean takeHit() {
        return false;
    }

    @Override
    public void move(double deltaTime) {
        if (movingLeft) x -= speed * deltaTime;
        if (movingRight) x += speed * deltaTime;

        if (x < 0) x = 0;
        if (x + width > WINDOW_WIDTH) x = WINDOW_WIDTH - width;
    }

    // ---------------------
    // Power-up control
    // ---------------------

    public void expandTemporarily() {
        if (!expanded) {
            expanded = true;
            width = Math.min(originalWidth * 1.6, 300);
            expandEndTime = System.currentTimeMillis() + 7000;
            System.out.println("Paddle expanded!");
        } else {
            expandEndTime = System.currentTimeMillis() + 5000;
        }
    }

    public void removeAllPowerUpEffects() {
        expanded = false;
        width = originalWidth;
        laserActive = false;
        laserPowerUpInEffect = false;
        immortalPowerUpInEffect = false;
    }

    // ---------------------
    // Getters & Setters
    // ---------------------

    public boolean isLaserPowerUpInEffect() { return laserPowerUpInEffect; }
    public void setLaserPowerUpInEffect(boolean value) { this.laserPowerUpInEffect = value; }
    public boolean isLaserInterrupted() { return laserInterrupted; }
    public void setLaserInterrupted(boolean laserInterrupted) { this.laserInterrupted = laserInterrupted; }

    public boolean isImmortalPowerUpInEffect() { return immortalPowerUpInEffect; }
    public void setImmortalPowerUpInEffect(boolean immortalPowerUpInEffect) {
        this.immortalPowerUpInEffect = immortalPowerUpInEffect;
    }

    public void setMovingLeft(boolean value) { movingLeft = value; }

    public void setMovingRight(boolean value) { movingRight = value; }
}
