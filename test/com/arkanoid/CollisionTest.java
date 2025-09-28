package com.arkanoid;

import com.arkanoid.entity.Ball;
import com.arkanoid.entity.GameObject;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {

    // Class giả lập object va chạm (ví dụ 1 block)
    static class Block extends GameObject {
        public Block(double x, double y, int w, int h) {
            super(x, y, w, h);
        }

        @Override
        public void update() {

        }

        @Override
        public void render(Graphics g) {

        }

        @Override
        public Rectangle2D getBounds() {
            return new Rectangle2D(x, y, width, height);
        }
    }

    @Test
    public void testCollisionDetected() {
        Ball ball = new Ball(50, 50, 1, 0, 5, 10);
        Block block = new Block(55, 50, 20, 20);

        assertTrue(ball.checkCollision(block), "Ball phải va chạm với block");
    }

    @Test
    public void testCollisionNotDetected() {
        Ball ball = new Ball(0, 0, 1, 0, 5, 10);
        Block block = new Block(100, 100, 20, 20);

        assertFalse(ball.checkCollision(block), "Ball không nên va chạm block");
    }

    @Test
    public void testBounceOffHorizontalWall() {
        Ball ball = new Ball(50, 50, 1, 0, 5, 10);
        Block wall = new Block(60, 40, 20, 20);

        if (ball.checkCollision(wall)) {
            ball.bounceOff(wall);
        }

        assertTrue(ball.getDx() < 0, "Ball phải bật ngược lại theo trục X");
    }

    @Test
    public void testBounceOffVerticalWall() {
        Ball ball = new Ball(50, 50, 0, 1, 5, 10);
        Block wall = new Block(40, 60, 20, 20);

        if (ball.checkCollision(wall)) {
            ball.bounceOff(wall);
        }

        assertTrue(ball.getDy() < 0, "Ball phải bật ngược lại theo trục Y");
    }

    @Test
    public void testBounceOffCorner() {
        // Ball ở gần (50, 50), đi chéo xuống phải
        Ball ball = new Ball(50, 50, 1, 1, 5, 10);
        // Đặt block ở góc dưới bên phải của ball
        Block cornerBlock = new Block(60, 60, 20, 20);

        if (ball.checkCollision(cornerBlock)) {
            ball.bounceOff(cornerBlock);
        }

        // Sau khi bật khỏi góc, dx và dy đều phải đảo dấu
        assertTrue(ball.getDx() < 0, "Ball phải bật ngược lại theo trục X khi đập góc");
        assertTrue(ball.getDy() < 0, "Ball phải bật ngược lại theo trục Y khi đập góc");
    }
}
