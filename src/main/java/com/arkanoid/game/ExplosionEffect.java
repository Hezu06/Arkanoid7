package com.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ExplosionEffect {
    private double x, y;
    private final double TARGET_SIZE;    // Final size of the visual effect.
    private double timeAlive = 0;

    private final double DURATION = 2;
    private final Image SPRITE_SHEET;
    private boolean finished = false;

    private final int TOTAL_FRAME = 20;
    private final int FRAME_PER_ROW = 4;
    private final int FRAME_PER_COL = 5;
    private final double FRAME_DURATION = DURATION / TOTAL_FRAME;
    private final double SHEET_WIDTH;
    private final double SHEET_HEIGHT;
    private final double FRAME_WIDTH;
    private final double FRAME_HEIGHT;

    private double frameTimer = 0;
    private int currentFrame = 0;

    private static final String EXPLOSION_SHEET_PATH = "assets/Bricks/EffectExplosion/explosionSprite.png";

    public ExplosionEffect(double x, double y, double width, double height) {
        this.TARGET_SIZE = Math.max(width, height) * 2; // Explosion is twice the brick's size.
        this.x = x + width / 2;
        this.y = y + height / 2;

        // Safe check before loading the sprite sheet.
        Image loadedSheet = null;
        try {
            loadedSheet = new Image(EXPLOSION_SHEET_PATH);
        } catch (Exception e) {
            System.err.println("Explosion sheet NOT FOUND!");
        }
        this.SPRITE_SHEET = loadedSheet;

        if (SPRITE_SHEET != null) {
            this.SHEET_WIDTH = SPRITE_SHEET.getWidth();
            this.SHEET_HEIGHT = SPRITE_SHEET.getHeight();
            // Calculate frame dimensions.
            this.FRAME_WIDTH = this.SHEET_WIDTH / FRAME_PER_ROW;
            this.FRAME_HEIGHT = this.SHEET_HEIGHT / FRAME_PER_COL;
        } else {
            this.SHEET_WIDTH = 0;
            this.SHEET_HEIGHT = 0;
            this.FRAME_WIDTH = 0;
            this.FRAME_HEIGHT = 0;
        }
    }

    public void update(double deltaTime) {
        timeAlive += deltaTime;
        frameTimer += deltaTime;

        if (frameTimer >= FRAME_DURATION) {
            currentFrame++;
            frameTimer = 0;
        }

        if (currentFrame >= TOTAL_FRAME) {
            finished = true;
        }
    }


    public void render(GraphicsContext gc) {
        if (finished || SPRITE_SHEET == null) { return; }

        if (!SPRITE_SHEET.isError() && SPRITE_SHEET.getWidth() > 0) {
            double alpha = 1.0 - (timeAlive / DURATION * 0.2);
            gc.setGlobalAlpha(alpha);

            // Calculate source position on the sprite sheet.
            int col = currentFrame % FRAME_PER_ROW;
            int row = currentFrame / FRAME_PER_ROW;

            double srcX = col * FRAME_WIDTH;
            double srcY = row * FRAME_HEIGHT;

            // Draw Img on the canvas.
            // Parameters: (Image, SrcX, SrcY, SrcW, SrcH, DestX, DestY, DestW, DestH)
            gc.drawImage(
                    SPRITE_SHEET,
                    srcX, srcY,
                    FRAME_WIDTH, FRAME_HEIGHT,
                    x - TARGET_SIZE / 2, y - TARGET_SIZE / 2, TARGET_SIZE, TARGET_SIZE
            );

            gc.setGlobalAlpha(1.0);
        } else {
            System.err.println("Cannot render the sprite sheet to the screen.");
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
