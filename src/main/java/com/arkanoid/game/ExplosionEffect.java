package com.arkanoid.game;

import com.arkanoid.entity.brick.ExplosiveBrick;
import com.arkanoid.entity.brick.NormalBrick;
import com.arkanoid.entity.brick.StrongBrick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import com.arkanoid.entity.brick.Brick;



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
    private final Random rand = new Random();
    private boolean finished = false;

    public ExplosionEffect(double x, double y, double width, double height, Brick brick) {
        double centerX = x + width / 2;
        double centerY = y + height / 2;

        /* Explosive brick: Bigger explosion size and number of particles.
            Number of particles: 80.
            Size: 3 + rand(0 - 1) x 4.
            Life: 0.6 + rand(0 - 1) x 0.8.
            Speed: 120 + rand(0 - 1) x 180.
            Color: Orange -> Yellow.
         */
        /* Other brick: Smaller explosion size and number of particles.
            Number of particles: 30.
            Size: 2 + rand(0 - 1) * 2.
            Life: 0.2 + rand(0 - 1) x 0.4.
            Speed: 100 + rand(0 - 1) x 150.
            Color: Brick Color.
         */

        // ---- About hsb() ----
        // Hue (0.0): Red
        // Saturation (1.0): Full color
        // Brightness (1.0): Full brightness

        int numsOfParticles = 0;
        int speedConstant1 = 0;
        int speedConstant2 = 0;
        double lifeConstant1 = 0.0;
        double lifeConstant2 = 0.0;
        int sizeConstant1 = 0;
        int sizeConstant2 = 0;
        Color color = null;

        if (brick instanceof ExplosiveBrick) {
            /* Explosive brick (BIG)
                Number of particles: 80.
                Size: 3 + rand(0 - 1) x 4.
                Life: 0.6 + rand(0 - 1) x 0.8.
                Speed: 120 + rand(0 - 1) x 180.
                Color: Orange -> Yellow.
             */
            numsOfParticles = 80;
            speedConstant1 = 120;
            speedConstant2 = 180;
            lifeConstant1 = 0.6;
            lifeConstant2 = 0.8;

            // Particle size range 3.0 to 7.0
            sizeConstant1 = 3;
            sizeConstant2 = 3;

            // From Orange to Yellow (Hue 30 to 60)
            color = Color.hsb(30 + 30 * rand.nextDouble(), 0.9, 0.9);
        }
        else {
            /* Other bricks (SMALL)
                Number of particles: 50. (Adjusted from 30 to 50 for a visible effect)
                Size: 2 + rand(0 - 1) * 3. (Adjusted constant 2 to 3 for better range)
                Life: 0.4 + rand(0 - 1) x 0.6. (Adjusted constants)
                Speed: 80 + rand(0 - 1) x 120. (Adjusted constants)
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
                color = Color.hsb(300 + 30 * rand.nextDouble(), 0.9, 0.9);
                // Pink/Magenta (Hue 300 to 330)
            }
            else  {
                // StrongBrick.
                color = Color.hsb(190 + 20 * rand.nextDouble(), 0.5, 0.5);
                // Steel Gray / Cool Blue (Hue 190 to 210, Low Saturation/Brightness)
            }
        }

        // spawn particles
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
