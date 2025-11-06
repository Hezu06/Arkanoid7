package UnitTest;
import com.arkanoid.entity.Ball;
import com.arkanoid.entity.GameObject;
import com.arkanoid.entity.Paddle;
import javafx.geometry.Rectangle2D;
// import javafx.scene.image.WritableImage; // ĐÃ XÓA
// import org.junit.jupiter.api.BeforeAll; // ĐÃ XÓA
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

    private static final double DELTA = 0.001;

    @Test
    @DisplayName("checkCollision: Trả về TRUE khi bóng LỒNG vào vật thể")
    void testCollision_WhenOverlapping_ShouldReturnTrue() {
        // Bóng (10,10) bán kính 5 (tâm 15,15)
        Ball ball = new Ball(10, 10, 0, 0, 100, 5, null); // Truyền null
        // Gạch (12,12) rộng 10, cao 10
        GameObject brick = new MockGameObject(12, 12, 10, 10);

        assertTrue(ball.checkCollision(brick), "Bóng nên được phát hiện va chạm khi lồng vào gạch");
    }

    @Test
    @DisplayName("checkCollision: Trả về FALSE khi bóng ở XA vật thể")
    void testCollision_WhenFarAway_ShouldReturnFalse() {
        // Bóng (10,10) bán kính 5 (tâm 15,15)
        Ball ball = new Ball(10, 10, 0, 0, 100, 5, null); // Truyền null
        // Gạch (100,100)
        GameObject brick = new MockGameObject(100, 100, 10, 10);

        assertFalse(ball.checkCollision(brick), "Bóng không nên va chạm khi ở xa");
    }

    @Test
    @DisplayName("checkCollision: Trả về TRUE khi bóng CHẠM CẠNH vật thể")
    void testCollision_WhenTouchingEdge_ShouldReturnTrue() {
        // Bóng (10,10) bán kính 5 (tâm 15,15)
        Ball ball = new Ball(10, 10, 0, 0, 100, 5, null); // Truyền null
        // Gạch (20,10) (cạnh trái của gạch ở x=20, vừa chạm vào cạnh phải của bóng ở x=20)
        GameObject brick = new MockGameObject(20, 10, 10, 10);

        assertTrue(ball.checkCollision(brick), "Bóng nên va chạm khi chạm cạnh");
    }

    @Test
    @DisplayName("checkCollision: Trả về TRUE khi bóng CHẠM GÓC vật thể")
    void testCollision_WhenTouchingCorner_ShouldReturnTrue() {
        // Bóng (0,0) bán kính 5 (tâm 5,5)
        Ball ball = new Ball(0, 0, 0, 0, 100, 5, null); // Truyền null
        // Gạch (8,8)
        // Tâm bóng (5,5), điểm gần nhất trên gạch là (8,8)
        // distSq = (8-5)^2 + (8-5)^2 = 3^2 + 3^2 = 18
        // radiusSq = 5^2 = 25.   18 <= 25 -> Va chạm
        GameObject brick = new MockGameObject(8, 8, 10, 10);

        assertTrue(ball.checkCollision(brick), "Bóng nên va chạm khi chạm góc");
    }

    // --- Test cho bounceOff(GameObject other) ---

    @Test
    @DisplayName("bounceOff: Nảy khỏi tường TRÊN (đảo chiều Y)")
    void testBounce_FromTopWall_ShouldReverseY() {
        // Bóng (100, 10) r=5, đi LÊN (dy=-1)
        Ball ball = new Ball(100, 10, 0, -1, 100, 5, null); // Truyền null
        // Tường trên (0,0) cao 5
        GameObject topWall = new MockGameObject(0, 0, 800, 5);

        ball.bounceOff(topWall);

        assertTrue(ball.getDy() > 0, "Vận tốc Y nên đảo ngược (thành dương) sau khi va chạm tường trên");
    }

    @Test
    @DisplayName("bounceOff: Nảy khỏi gạch DƯỚI (đảo chiều Y)")
    void testBounce_FromBottomBrick_ShouldReverseY() {
        // Bóng (100, 80) r=5, đi XUỐNG (dy=1)
        Ball ball = new Ball(100, 80, 0, 1, 100, 5, null); // Truyền null
        // Gạch (50, 90) (mặt trên gạch ở y=90, bóng ở y=80 + 10 = 90)
        GameObject brick = new MockGameObject(50, 90, 100, 10);

        ball.bounceOff(brick);

        assertTrue(ball.getDy() < 0, "Vận tốc Y nên đảo ngược (thành âm) sau khi va chạm gạch dưới");
    }

    @Test
    @DisplayName("bounceOff: Nảy khỏi tường TRÁI (đảo chiều X)")
    void testBounce_FromLeftWall_ShouldReverseX() {
        // Bóng (10, 100) r=5, đi TRÁI (dx=-1)
        Ball ball = new Ball(10, 100, -1, 0, 100, 5, null); // Truyền null
        // Tường trái (0,0) rộng 5
        GameObject leftWall = new MockGameObject(0, 0, 5, 800);

        ball.bounceOff(leftWall);

        assertTrue(ball.getDx() > 0, "Vận tốc X nên đảo ngược (thành dương) sau khi va chạm tường trái");
    }

    @Test
    @DisplayName("bounceOff: Nảy khỏi tường PHẢI (đảo chiều X)")
    void testBounce_FromRightWall_ShouldReverseX() {
        // Bóng (730, 100) r=5, đi PHẢI (dx=1)
        // (WINDOW_WIDTH = 750). Bóng (x=730, r=5 -> tâm 735, cạnh phải 740)
        Ball ball = new Ball(730, 100, 1, 0, 100, 5, null); // Truyền null
        // Tường phải (745, 0)
        GameObject rightWall = new MockGameObject(745, 0, 5, 800);

        ball.bounceOff(rightWall);

        assertTrue(ball.getDx() < 0, "Vận tốc X nên đảo ngược (thành âm) sau khi va chạm tường phải");
    }

    // --- Test cho bounceOff(Paddle paddle) ---

    @Test
    @DisplayName("bounceOff(Paddle): Nảy ở GIỮA paddle (bay thẳng LÊN)")
    void testBounce_FromPaddleCenter_ShouldGoStraightUp() {
        // Bóng (tâm 400) đi XUỐNG
        // Bán kính bóng là 10 (r=10)
        Ball ball = new Ball(390, 700, 0, 1, 100, 10, null); // Truyền null
        // Paddle (x=350, y=710, w=100)
        Paddle paddle = new Paddle(350, 710, 100, 10, null); // Truyền null

        // Vị trí va chạm: (tâm 400 - x 350) / rộng 100 = 50 / 100 = 0.5
        // Góc: 150 * (0.5 - 0.5) = 0 độ
        // dx = sin(0) = 0
        // dy = -cos(0) = -1

        ball.bounceOff(paddle);

        assertEquals(0.0, ball.getDx(), DELTA, "Vận tốc X nên bằng 0");
        assertEquals(-1.0, ball.getDy(), DELTA, "Vận tốc Y nên là -1");
    }

    @Test
    @DisplayName("bounceOff(Paddle): Nảy ở MÉP TRÁI paddle (bay LÊN và TRÁI)")
    void testBounce_FromPaddleLeft_ShouldGoUpAndLeft() {
        // Bóng (tâm 360) đi XUỐNG
        Ball ball = new Ball(350, 700, 0, 1, 100, 10, null); // Truyền null
        // Paddle (x=350, y=710, w=100)
        Paddle paddle = new Paddle(350, 710, 100, 10, null); // Truyền null

        // Vị trí va chạm: (tâm 360 - x 350) / rộng 100 = 10 / 100 = 0.1
        // Góc: 150 * (0.1 - 0.5) = 150 * -0.4 = -60 độ
        // dx = sin(-60) = -0.866
        // dy = -cos(-60) = -0.5

        ball.bounceOff(paddle);

        assertEquals(-0.866, ball.getDx(), DELTA, "Vận tốc X nên là -0.866");
        assertEquals(-0.5, ball.getDy(), DELTA, "Vận tốc Y nên là -0.5");
    }

    @Test
    @DisplayName("bounceOff(Paddle): Nảy ở MÉP PHẢI paddle (bay LÊN và PHẢI)")
    void testBounce_FromPaddleRight_ShouldGoUpAndRight() {
        // Bóng (tâm 440) đi XUỐNG
        Ball ball = new Ball(430, 700, 0, 1, 100, 10, null); // Truyền null
        // Paddle (x=350, y=710, w=100)
        Paddle paddle = new Paddle(350, 710, 100, 10, null); // Truyền null

        // Vị trí va chạm: (tâm 440 - x 350) / rộng 100 = 90 / 100 = 0.9
        // Góc: 150 * (0.9 - 0.5) = 150 * 0.4 = 60 độ
        // dx = sin(60) = 0.866
        // dy = -cos(60) = -0.5

        ball.bounceOff(paddle);

        assertEquals(0.866, ball.getDx(), DELTA, "Vận tốc X nên là 0.866");
        assertEquals(-0.5, ball.getDy(), DELTA, "Vận tốc Y nên là -0.5");
    }
}

