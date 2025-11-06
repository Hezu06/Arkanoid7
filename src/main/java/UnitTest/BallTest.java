package UnitTest;

import com.arkanoid.entity.Ball;
import com.arkanoid.entity.GameObject;
import com.arkanoid.entity.Paddle;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp MockGameObject đơn giản để giả lập Gạch (Brick) hoặc Tường (Wall)
 * Nó chỉ cần cung cấp một Rectangle2D (vùng va chạm).
 */
class MockGameObject extends GameObject {
    private final Rectangle2D bounds;

    public MockGameObject(double x, double y, double w, double h) {
        super(x, y, (int) w, (int) h);
        this.bounds = new Rectangle2D(x, y, w, h);
    }

    @Override
    public void update() {
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc) {
        // Không cần implement
    }

    @Override
    public boolean takeHit() {
        return false; // Không cần implement
    }
}


@DisplayName("Thử nghiệm Lớp Ball - Va chạm & Nảy")
class BallTest {

    // Sai số cho phép khi so sánh số double
    private static final double DELTA = 0.001;

    // --- Test cho checkCollision() ---

    @Test
    @DisplayName("checkCollision: Trả về TRUE khi bóng LỒNG vào vật thể")
    void testCollision_WhenOverlapping_ShouldReturnTrue() {
        // Bóng (10,10) bán kính 5 (tâm 15,15)
        Ball ball = new Ball(10, 10, 0, 0, 100, 5, null);
        // Gạch (12,12) rộng 10, cao 10
        GameObject brick = new MockGameObject(12, 12, 10, 10);

        assertTrue(ball.checkCollision(brick), "Bóng nên được phát hiện va chạm khi lồng vào gạch");
    }

    @Test
    @DisplayName("checkCollision: Trả về FALSE khi bóng ở XA vật thể")
    void testCollision_WhenFarAway_ShouldReturnFalse() {
        // Bóng (10,10) bán kính 5 (tâm 15,15)
        Ball ball = new Ball(10, 10, 0, 0, 100, 5, null);
        // Gạch (100,100)
        GameObject brick = new MockGameObject(100, 100, 10, 10);

        assertFalse(ball.checkCollision(brick), "Bóng không nên va chạm khi ở xa");
    }

    @Test
    @DisplayName("checkCollision: Trả về TRUE khi bóng CHẠM CẠNH vật thể")
    void testCollision_WhenTouchingEdge_ShouldReturnTrue() {
        // Bóng (10,10) bán kính 5 (cạnh phải bóng ở x=20)
        Ball ball = new Ball(10, 10, 0, 0, 100, 5, null);
        // Gạch (20,10) (cạnh trái của gạch ở x=20)
        GameObject brick = new MockGameObject(20, 10, 10, 10);

        assertTrue(ball.checkCollision(brick), "Bóng nên va chạm khi chạm cạnh");
    }

    @Test
    @DisplayName("checkCollision: Trả về TRUE khi bóng CHẠM GÓC vật thể")
    void testCollision_WhenTouchingCorner_ShouldReturnTrue() {
        // Bóng (0,0) bán kính 5 (tâm 5,5)
        Ball ball = new Ball(0, 0, 0, 0, 100, 5, null);
        // Gạch (8,8) (điểm gần nhất 8,8)
        GameObject brick = new MockGameObject(8, 8, 10, 10);
        // (8-5)^2 + (8-5)^2 = 18. radius^2 = 25. 18 <= 25 -> Va chạm
        assertTrue(ball.checkCollision(brick), "Bóng nên va chạm khi chạm góc");
    }

    // --- Test cho bounceOff(GameObject other) ---

    @Test
    @DisplayName("bounceOff: Nảy khỏi tường TRÊN (đảo chiều Y)")
    void testBounce_FromTopWall_ShouldReverseY() {
        // Bóng (100, 0) r=5, đi LÊN (dy=-1). Cạnh trên bóng y=0.
        Ball ball = new Ball(100, 0, 0, -1, 100, 5, null);
        // Tường trên (0,0) cao 5. Cạnh dưới tường y=5.
        GameObject topWall = new MockGameObject(0, 0, 800, 5);

        ball.bounceOff(topWall);

        assertTrue(ball.getDy() > 0, "Vận tốc Y nên đảo ngược (thành dương)");
    }

    @Test
    @DisplayName("bounceOff: Nảy khỏi gạch DƯỚI (Test lỗi Overlap=0)")
    void testBounce_FromBottomBrick_ShouldReverseY() {
        // Bóng (100, 80) r=5, đi XUỐNG (dy=1). Cạnh dưới bóng y=90.
        Ball ball = new Ball(100, 80, 0, 1, 100, 5, null);
        // Gạch (50, 90) (mặt trên gạch ở y=90)
        GameObject brick = new MockGameObject(50, 90, 100, 10);

        ball.bounceOff(brick);

        // Sẽ PASS nếu Ball.java dùng (overlapY >= 0)
        assertTrue(ball.getDy() < 0, "Vận tốc Y nên đảo ngược (thành âm)");
    }

    @Test
    @DisplayName("bounceOff: Nảy khỏi tường TRÁI (Test lỗi setup)")
    void testBounce_FromLeftWall_ShouldReverseX() {
        // Bóng (0, 100) r=5, đi TRÁI (dx=-1). Cạnh trái bóng x=0.
        Ball ball = new Ball(0, 100, -1, 0, 100, 5, null); // ĐÃ SỬA: x=0
        // Tường trái (0,0) rộng 5. Cạnh phải tường x=5.
        GameObject leftWall = new MockGameObject(0, 0, 5, 800);

        ball.bounceOff(leftWall);

        assertTrue(ball.getDx() > 0, "Vận tốc X nên đảo ngược (thành dương)");
    }

    @Test
    @DisplayName("bounceOff: Nảy khỏi tường PHẢI (Test lỗi setup)")
    void testBounce_FromRightWall_ShouldReverseX() {
        // Bóng (735, 100) r=5, đi PHẢI (dx=1). Cạnh phải bóng x=745.
        Ball ball = new Ball(735, 100, 1, 0, 100, 5, null); // ĐÃ SỬA: x=735
        // Tường phải (745, 0) rộng 5. Cạnh trái tường x=745.
        GameObject rightWall = new MockGameObject(745, 0, 5, 800);

        ball.bounceOff(rightWall);

        assertTrue(ball.getDx() < 0, "Vận tốc X nên đảo ngược (thành âm)");
    }

    // --- Test cho bounceOff(Paddle paddle) ---

    @Test
    @DisplayName("bounceOff(Paddle): Nảy ở GIỮA paddle (bay thẳng LÊN)")
    void testBounce_FromPaddleCenter_ShouldGoStraightUp() {
        // Bóng (390, 700) r=10 (tâm 400) đi XUỐNG
        Ball ball = new Ball(390, 700, 0, 1, 100, 10, null);
        // Paddle (x=350, y=710, w=100) (giữa 400)
        Paddle paddle = new Paddle(350, 710, 100, 10, null);

        // hitPos = (400 - 350) / 100 = 0.5. Angle = 150 * (0.5 - 0.5) = 0
        ball.bounceOff(paddle);

        assertEquals(0.0, ball.getDx(), DELTA, "Vận tốc X nên bằng 0 (sin 0°)");
        assertEquals(-1.0, ball.getDy(), DELTA, "Vận tốc Y nên là -1 (-cos 0°)");
    }

    @Test
    @DisplayName("bounceOff(Paddle): Nảy ở MÉP TRÁI paddle (bay LÊN và TRÁI)")
    void testBounce_FromPaddleLeft_ShouldGoUpAndLeft() {
        // Bóng (350, 700) r=10 (tâm 360) đi XUỐNG
        Ball ball = new Ball(350, 700, 0, 1, 100, 10, null);
        // Paddle (x=350, y=710, w=100)
        Paddle paddle = new Paddle(350, 710, 100, 10, null);

        // hitPos = (360 - 350) / 100 = 0.1. Angle = 150 * (0.1 - 0.5) = -60°
        ball.bounceOff(paddle);

        assertEquals(-0.866, ball.getDx(), DELTA, "Vận tốc X nên là -0.866 (sin -60°)");
        assertEquals(-0.5, ball.getDy(), DELTA, "Vận tốc Y nên là -0.5 (-cos -60°)");
    }

    @Test
    @DisplayName("bounceOff(Paddle): Nảy ở MÉP PHẢI paddle (bay LÊN và PHẢI)")
    void testBounce_FromPaddleRight_ShouldGoUpAndRight() {
        // Bóng (430, 700) r=10 (tâm 440) đi XUỐNG
        Ball ball = new Ball(430, 700, 0, 1, 100, 10, null);
        // Paddle (x=350, y=710, w=100)
        Paddle paddle = new Paddle(350, 710, 100, 10, null);

        // hitPos = (440 - 350) / 100 = 0.9. Angle = 150 * (0.9 - 0.5) = 60°
        ball.bounceOff(paddle);

        assertEquals(0.866, ball.getDx(), DELTA, "Vận tốc X nên là 0.866 (sin 60°)");
        assertEquals(-0.5, ball.getDy(), DELTA, "Vận tốc Y nên là -0.5 (-cos 60°)");
    }

    @Test
    @DisplayName("bounceOff(Paddle): Nảy ở MÉP TRÁI CÙNG (góc nảy tối đa)")
    void testBounce_FromPaddleExtremeLeft_ShouldGoMaxAngle() {
        // Bóng (340, 700) r=10 (tâm 350) đi XUỐNG
        Ball ball = new Ball(340, 700, 0, 1, 100, 10, null);
        // Paddle (x=350, y=710, w=100)
        Paddle paddle = new Paddle(350, 710, 100, 10, null);

        // hitPos = (350 - 350) / 100 = 0.0. Angle = 150 * (0.0 - 0.5) = -75°
        ball.bounceOff(paddle);

        assertEquals(-0.966, ball.getDx(), DELTA, "Vận tốc X nên là -0.966 (sin -75°)");
        assertEquals(-0.259, ball.getDy(), DELTA, "Vận tốc Y nên là -0.259 (-cos -75°)");
    }

    @Test
    @DisplayName("bounceOff(Paddle): Nảy khỏi CẠNH paddle (dùng logic gạch)")
    void testBounce_FromPaddleSide_ShouldReverseX() {
        // Bóng (330, 712) r=10 (tâm 340, 722) đi NGANG (dx=1)
        Ball ball = new Ball(330, 712, 1, 0, 100, 10, null);
        // Paddle (x=350, y=710, w=100, h=10)
        Paddle paddle = new Paddle(350, 710, 100, 10, null);

        // Logic paddle đặc biệt sẽ KHÔNG chạy vì (cy < rect.getMinY()) -> (722 < 710) là FALSE.
        // Code sẽ "rơi" xuống logic va chạm chung (như Gạch).
        ball.bounceOff(paddle);

        assertTrue(ball.getDx() < 0, "Vận tốc X nên đảo ngược (dùng logic gạch)");
        assertEquals(0.0, ball.getDy(), DELTA, "Vận tốc Y nên không đổi");
    }
}