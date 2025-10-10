package com.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ExplosionEffect {
    private static class Particle {
        double x, y;
        double vx, vy;
        double life;
        double size;
        Color color;

        Particle(double x, double y, double vx, double vy, double life, double size, Color color) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.life = life;
            this.size = size;
            this.color = color;
        }
    }

    private final List<Particle> particles = new ArrayList<>();
    private final Random rand = new Random();
    private boolean finished = false;

    public ExplosionEffect(double x, double y, double width, double height) {
        double cx = x + width / 2;
        double cy = y + height / 2;

        // spawn particles
        for (int i = 0; i < 40; i++) {
            double angle = rand.nextDouble() * 2 * Math.PI;
            double speed = 120 + rand.nextDouble() * 180;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            double life = 0.8 + rand.nextDouble() * 0.6;
            double size = 3 + rand.nextDouble() * 4;
            Color color = Color.hsb(30 + rand.nextDouble() * 30, 1, 1);
            particles.add(new Particle(cx, cy, vx, vy, life, size, color));
        }
    }

    public void update(double deltaTime) {
        Iterator<Particle> iter = particles.iterator();
        while (iter.hasNext()) {
            Particle p = iter.next();
            p.x += p.vx * deltaTime;
            p.y += p.vy * deltaTime;
            p.vy += 100 * deltaTime; // slight gravity
            p.life -= deltaTime;
            if (p.life <= 0) iter.remove();
        }
        finished = particles.isEmpty();
    }

    public void render(GraphicsContext gc) {
        for (Particle p : particles) {
            double alpha = Math.max(0, p.life);
            gc.setGlobalAlpha(alpha);
            gc.setFill(p.color);
            gc.fillOval(p.x - p.size / 2, p.y - p.size / 2, p.size, p.size);
        }
        gc.setGlobalAlpha(1.0);
    }

    public boolean isFinished() {
        return finished;
    }
}
