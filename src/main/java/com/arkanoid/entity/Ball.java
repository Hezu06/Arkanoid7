package com.arkanoid.entity;

import com.arkanoid.entity.powerUp.PowerUp;
import com.arkanoid.game.GameMain;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.util.Objects;

public class Ball extends MovableObject {
    private final double speed;
    private final double radius;
    private static Image image;
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 800;
    private final AudioClip hitSound;
    private static int numberOfBalls = 0;

    private boolean isFireBall = false;
    private double fireBallTimer = 0.0;
    private static final double FIREBALL_DURATION = 5.0;

    private boolean alive = true;

    public Ball(double x, double y, double dx, double dy, double speed, double radius, Image image) {
        super(x, y, (int) (2 * radius), (int) (2 * radius), dx, dy);
        this.speed = speed;
        this.radius = radius;
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            this.dx /= length;
            this.dy /= length;
        }
        numberOfBalls++;
        Ball.image = image;
        hitSound = new AudioClip(
                Objects.requireNonNull(getClass().getResource("/sounds/collision_sound.wav")).toExternalForm()
        );
    }

    public static void setImage(Image image) {
        Ball.image = image;
    }

    public void bounceOff(GameObject other) {
        double cx = getCenterX();
        double cy = getCenterY();

        // --- Paddle Logic (Logic cho Paddle) ---
        if (other instanceof Paddle paddle) {
            Rectangle2D rect = paddle.getBounds();

            // Chỉ kích hoạt logic paddle đặc biệt nếu bóng đập vào MẶT TRÊN của paddle
            // và đang đi XUỐNG (dy > 0).
            // (cy < rect.getMinY() dùng để kiểm tra bóng ở phía trên paddle)
            if (cy < rect.getMinY() && dy > 0) {
                double hitPos = (cx - paddle.getX()) / paddle.getWidth();
                hitPos = Math.max(0, Math.min(1, hitPos)); // Giới hạn từ 0 đến 1
                // Góc nảy từ ±75 độ (150 * (hitPos - 0.5))
                double angle = Math.toRadians(150 * (hitPos - 0.5));
                dx = Math.sin(angle);
                dy = -Math.cos(angle); // Luôn nảy lên
                y = paddle.getY() - radius * 2 - 0.1; // Đẩy bóng ra khỏi paddle một chút
                hitSound.play();
                return; // Kết thúc sớm vì logic paddle là đặc biệt
            }
            // Nếu bóng đập vào CẠNH của paddle, nó sẽ rơi vào logic chung (Brick/Wall)
        }

        // --- Logic chung (Brick / Wall) ---
        // Sử dụng logic "Minimum Penetration" (Độ xuyên thấu tối thiểu)
        // để quyết định nảy theo chiều ngang hay chiều dọc.

        Rectangle2D rect = other.getBounds();

        // 1. Tìm tâm của gạch/tường
        double rx = rect.getMinX() + rect.getWidth() / 2;
        double ry = rect.getMinY() + rect.getHeight() / 2;

        // 2. Tìm tổng "nửa-chiều-rộng" và "nửa-chiều-cao"
        double sumHalfWidths = radius + rect.getWidth() / 2;
        double sumHalfHeights = radius + rect.getHeight() / 2;

        // 3. Tìm khoảng cách giữa các tâm
        double diffX = cx - rx;
        double diffY = cy - ry;

        // 4. Tính toán độ xuyên thấu (overlap) trên cả hai trục
        // Chúng ta quan tâm đến giá trị tuyệt đối của diffX/diffY
        double overlapX = sumHalfWidths - Math.abs(diffX);
        double overlapY = sumHalfHeights - Math.abs(diffY);

        // Đảm bảo rằng có va chạm (overlap > 0 trên cả hai trục)
        // Mặc dù checkCollision đã làm điều này, kiểm tra lại ở đây cũng không sao
        if (overlapX > 0 && overlapY > 0) {

            // 5. So sánh độ xuyên thấu: Trục nào có độ xuyên thấu ÍT HƠN
            // chính là trục xảy ra va chạm.
            if (overlapX < overlapY) {
                // Va chạm theo chiều ngang (Trái/Phải)
                dx = -dx; // Lật hướng di chuyển ngang

                // Đẩy bóng ra khỏi vật thể để tránh bị kẹt
                if (diffX > 0) { // Bóng ở bên phải gạch
                    x = rect.getMaxX();
                } else { // Bóng ở bên trái gạch
                    x = rect.getMinX() - radius * 2;
                }
            } else {
                // Va chạm theo chiều dọc (Trên/Dưới)
                dy = -dy; // Lật hướng di chuyển dọc

                // Đẩy bóng ra khỏi vật thể để tránh bị kẹt
                if (diffY > 0) { // Bóng ở bên dưới gạch
                    y = rect.getMaxY();
                } else { // Bóng ở bên trên gạch
                    y = rect.getMinY() - radius * 2;
                }
            }
        }

        // Chuẩn hóa vector vận tốc (giữ lại từ code gốc của bạn, điều này tốt)
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len != 0) {
            dx /= len;
            dy /= len;
        }

        hitSound.play();
    }
    public void reverseY() {
        dy = -dy;
    }


    /**
     * Kiểm tra va chạm (Không thay đổi).
     * Logic này đã chính xác để PHÁT HIỆN va chạm giữa hình tròn và hình chữ nhật.
     */
    public boolean checkCollision(GameObject other) {
        Rectangle2D rect = other.getBounds();

        double cx = getCenterX();
        double cy = getCenterY();

        // Tìm điểm gần nhất trên hình chữ nhật so với tâm quả bóng
        double closestX = Math.max(rect.getMinX(), Math.min(cx, rect.getMaxX()));
        double closestY = Math.max(rect.getMinY(), Math.min(cy, rect.getMaxY()));

        // Tính khoảng cách bình phương từ tâm bóng đến điểm gần nhất
        double dxBall = cx - closestX;
        double dyBall = cy - closestY;

        double distanceSq = (dxBall * dxBall + dyBall * dyBall);

        // Nếu khoảng cách bình phương nhỏ hơn bán kính bình phương, thì có va chạm
        return distanceSq <= (radius * radius);
    }

    // ... (Phần còn lại của class giữ nguyên) ...

    public double getCenterX() {
        return x + radius;
    }

    public double getCenterY() {
        return y + radius;
    }

    public void move(double deltaTime) {
        if (isFireBall) {
            fireBallTimer -= deltaTime;
            if (fireBallTimer <= 0) {
                isFireBall = false; // Hết thời gian
                Ball.setImage(GameMain.ballTexture.getImage());
            }
        }

        // Tăng số bước để cải thiện độ chính xác va chạm (chia nhỏ bước đi)
        // Điều này giúp ngăn bóng "lọt" qua các vật thể mỏng ở tốc độ cao
        int steps = (int) Math.ceil(speed * deltaTime / (radius * 0.5)); // Tăng số bước
        if (steps == 0) steps = 1;
        double stepTime = deltaTime / steps;

        for (int i = 0; i < steps; i++) {
            x += dx * speed * stepTime;
            y += dy * speed * stepTime;

            // kiểm tra va chạm tường trong từng bước nhỏ
            if (x <= 0) {
                x = 0;
                dx = Math.abs(dx);
                hitSound.play();
            }

            if (x + radius * 2 >= WINDOW_WIDTH) {
                x = WINDOW_WIDTH - radius * 2;
                dx = -Math.abs(dx);
                hitSound.play();
            }

            if (y <= 0) {
                y = 0;
                dy = Math.abs(dy);
                hitSound.play();
            }

            if (y > WINDOW_HEIGHT) {
                alive = false;
                numberOfBalls--;
                return; // bóng đã rơi, dừng lại
            }
        }
    }


    public Rectangle2D getBounds() {
        return new Rectangle2D(
                x,
                y,
                radius * 2,
                radius * 2
        );
    }

    @Override
    public void render(GraphicsContext gc) {
        if (image.isError()) {
            System.out.println("Can't render ball");
        }
        gc.drawImage(image, x, y, radius * 2, radius * 2);
    }


    public static int getNumberOfBalls() {
        return numberOfBalls;
    }

    public static void setNumberOfBalls(int numberOfBalls) {
        Ball.numberOfBalls = numberOfBalls;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public boolean takeHit() {
        return y > WINDOW_HEIGHT;
    }

    public void activateFireBall() {
        this.isFireBall = true;
        this.fireBallTimer = FIREBALL_DURATION;
    }

    public boolean isFireBall() {
        return isFireBall;
    }

    public double getRadius() {
        return radius;
    }
}