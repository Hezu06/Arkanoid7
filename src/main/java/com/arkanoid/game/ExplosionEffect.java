package com.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.URL;

public class ExplosionEffect {
    private final double x;
    private final double y;
    private final double TARGET_SIZE;    // Final size of the visual effect.
    private double timeAlive = 0;

    private final double DURATION = 2.0;
    private final Image SPRITE_SHEET;
    private boolean finished = false;

    private final int TOTAL_FRAME = 20;
    // If your sheet is actually 5 columns × 4 rows, swap these values.
    private final int FRAME_COLS = 1;
    private final int FRAME_ROWS = 20;

    private final double FRAME_DURATION; // computed
    private final double SHEET_WIDTH;
    private final double SHEET_HEIGHT;
    private final double FRAME_WIDTH;
    private final double FRAME_HEIGHT;

    private double frameTimer = 0;
    private int currentFrame = 0;

    private static final String EXPLOSION_SHEET_CLASSPATH = "/assets/Bricks/EffectExplosion/explosionSprite.png";
    private static final String EXPLOSION_SHEET_FILE = "/assets/Bricks/EffectExplosion/explosionSprite.png";

    public ExplosionEffect(double x, double y, double width, double height) {
        this.TARGET_SIZE = Math.max(width, height) * 2; // Explosion is twice the brick's size.
        this.x = x + width / 2;
        this.y = y + height / 2;

        this.FRAME_DURATION = DURATION / TOTAL_FRAME;

        Image loaded = null;
        // 1) Try to load from classpath resource
        try {
            InputStream is = getClass().getResourceAsStream(EXPLOSION_SHEET_CLASSPATH);
            if (is != null) {
                loaded = new Image(is);
            } else {
                // 2) fallback: try loading as file (useful for running from IDE)
                URL fileUrl = getClass().getResource(EXPLOSION_SHEET_FILE);
                if (fileUrl != null) {
                    loaded = new Image(fileUrl.toExternalForm());
                } else {
                    // try as file path on filesystem
                    java.io.File f = new java.io.File(EXPLOSION_SHEET_FILE);
                    if (f.exists()) loaded = new Image(f.toURI().toString());
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load explosion sprite sheet: " + e.getMessage());
        }
        this.SPRITE_SHEET = loaded;

        if (SPRITE_SHEET != null && !SPRITE_SHEET.isError() && SPRITE_SHEET.getWidth() > 0) {
            this.SHEET_WIDTH = SPRITE_SHEET.getWidth();
            this.SHEET_HEIGHT = SPRITE_SHEET.getHeight();
            this.FRAME_WIDTH = this.SHEET_WIDTH / FRAME_COLS;
            this.FRAME_HEIGHT = this.SHEET_HEIGHT / FRAME_ROWS;
        } else {
            this.SHEET_WIDTH = 0;
            this.SHEET_HEIGHT = 0;
            this.FRAME_WIDTH = 0;
            this.FRAME_HEIGHT = 0;
            System.err.println("Explosion sprite sheet not loaded or has zero size.");
        }
    }

    public void update(double deltaTime) {
        if (finished) return;
        timeAlive += deltaTime;
        frameTimer += deltaTime;

        // advance how many frames passed (handles large deltaTime)
        int framesToAdvance = (int) (frameTimer / FRAME_DURATION);
        if (framesToAdvance > 0) {
            currentFrame += framesToAdvance;
            frameTimer = frameTimer % FRAME_DURATION;
        }

        if (currentFrame >= TOTAL_FRAME) {
            // keep last frame visible until object removed by game (or set finished true immediately)
            finished = true;
            currentFrame = TOTAL_FRAME - 1;
        }
    }

    public void render(GraphicsContext gc) {
        if (finished || SPRITE_SHEET == null || SPRITE_SHEET.isError()) { return; }

        if (FRAME_WIDTH <= 0 || FRAME_HEIGHT <= 0) return;

        gc.save(); // save context state (alpha, transforms)
        // fade out fully over DURATION; change as you like
        double alpha = Math.max(0.0, 1.0 - (timeAlive / DURATION));
        gc.setGlobalAlpha(alpha);

        int col = currentFrame % FRAME_COLS;
        int row = currentFrame / FRAME_COLS;

        double srcX = col * FRAME_WIDTH;
        double srcY = row * FRAME_HEIGHT;

        // defensive clamp (just in case)
        if (srcX + FRAME_WIDTH > SHEET_WIDTH) srcX = SHEET_WIDTH - FRAME_WIDTH;
        if (srcY + FRAME_HEIGHT > SHEET_HEIGHT) srcY = SHEET_HEIGHT - FRAME_HEIGHT;

        gc.drawImage(
                SPRITE_SHEET,
                srcX, srcY,
                FRAME_WIDTH, FRAME_HEIGHT,
                x - TARGET_SIZE / 2, y - TARGET_SIZE / 2, TARGET_SIZE, TARGET_SIZE
        );

        gc.restore();
        // debug (uncomment if you need)
        // System.out.printf("frame=%d col=%d row=%d srcX=%.1f srcY=%.1f fw=%.1f fh=%.1f%n",
        //         currentFrame, col, row, srcX, srcY, FRAME_WIDTH, FRAME_HEIGHT);
    }

    public boolean isFinished() {
        return finished;
    }
}
