package com.arkanoid.entity.brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class ExplosionEffect {

    private static class Particle {
        double x, y;
        double velocityX, velocityY;
        double life;
        double size;
        Color color;

        Particle(double x, double y,
                 double velocityX, double velocityY,
                 double life, double size,
                 Color color) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.life = life;
            this.size = size;
            this.color = color;
        }
    }

    private final List<Particle> particles = new ArrayList<>();
    private final Object particleLock = new Object();
    private boolean finished = false;

    public ExplosionEffect(double x, double y, double width, double height, Brick brick) {
        double centerX = x + width / 2;
        double centerY = y + height / 2;

        // ---- About hsb() ----
        // Hue (0.0): Red
        // Saturation (1.0): Full color
        // Brightness (1.0): Full brightness

        int numsOfParticles;
        int speedConstant1;
        int speedConstant2;
        double lifeConstant1;
        double lifeConstant2;
        int sizeConstant1;
        int sizeConstant2;
        Color color;

        Random rand = new Random();
        if (brick instanceof ExplosiveBrick) {
            /* Explosive brick (BIG)
                Number of particles: 80.
                Size: 3 + rand(0 - 1) x 4.
                Life: 0.6 + rand(0 - 1) x 0.8.
                Speed: 100 + rand(0 - 1) x 120.
                Color: Orange -> Yellow.
             */
            numsOfParticles = 80;
            speedConstant1 = 100;
            speedConstant2 = 120;
            lifeConstant1 = 0.6;
            lifeConstant2 = 0.8;

            // Particle size range 3.0 to 7.0
            sizeConstant1 = 3;
            sizeConstant2 = 3;

            // From Orange to Yellow (Hue 40 to 60)
            color = Color.hsb(40 + 20 * rand.nextDouble(), 0.9, 1);
        }
        else {
            /* Other bricks (SMALL)
                Number of particles: 40;
                Size: 2 + rand(0 - 1) x 3.
                Life: 0.3 + rand(0 - 1) x 0.5.
                Speed: 80 + rand(0 - 1) x 120.
                Color: Brick Color based on type.
             */
            numsOfParticles = 40;
            speedConstant1 = 60;
            speedConstant2 = 90;
            lifeConstant1 = 0.3;
            lifeConstant2 = 0.5;

            // Particle size range 2.0 to 5.0
            sizeConstant1 = 2;
            sizeConstant2 = 3;

            // ---- Set Color for specific Brick ----
            if (brick instanceof NormalBrick) {
                color = Color.hsb(300 + 30 * rand.nextDouble(), 0.5, 1);
                // Pink/Magenta (Hue 300 to 330)
            }
            else  {
                // StrongBrick.
                color = Color.hsb(190 + 20 * rand.nextDouble(), 0.5, 1);
                // Steel Gray / Cool Blue (Hue 190 to 210, Low Saturation/Brightness)
            }
        }

        // spawn particles
        synchronized (particleLock) {
            for (int i = 0; i < numsOfParticles; i++) {
                double angle = rand.nextDouble() * 2 * Math.PI;
                double speed = speedConstant1 + rand.nextDouble() * speedConstant2;
                double velocityX = Math.cos(angle) * speed;
                double velocityY = Math.sin(angle) * speed;
                double life = lifeConstant1 + rand.nextDouble() * lifeConstant2;
                double size = sizeConstant1 + rand.nextDouble() * sizeConstant2;

                particles.add(new Particle(centerX, centerY, velocityX, velocityY, life, size, color));
            }
        }
    }

    public void update(double deltaTime) {
        Iterator<Particle> iter = particles.iterator();
        while (iter.hasNext()) {
            Particle p = iter.next();
            p.x += p.velocityX * deltaTime;
            p.y += p.velocityY * deltaTime;
            p.velocityY += 100 * deltaTime; // slight gravity
            p.life -= deltaTime;
            if (p.life <= 0) iter.remove();
        }
        finished = particles.isEmpty();
    }

    public void render(GraphicsContext gc) {
        synchronized (particleLock) {
            for (Particle p : particles) {
                double alpha = Math.max(0, p.life);
                gc.setGlobalAlpha(alpha);
                gc.setFill(p.color);
                gc.fillOval(p.x - p.size / 2, p.y - p.size / 2, p.size, p.size);
            }
            gc.setGlobalAlpha(1.0);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
