package com.arkanoid.entity.brick;

import com.arkanoid.entity.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Brick extends GameObject {

    protected final int gridX, gridY;   // Identify bricks by numbering them. gridX = cols, gridY = rows.
    protected int currentDurability;
    protected final int maxDurability;
    protected boolean isBroken = false;

    private boolean fading = false;
    private double opacity = 1.0;

    protected Image textureImage;

    // --- Global Pulsing Timer ---
    protected static double pulseTimer = 0;
    private static final double PULSE_SPEED = 5.0; // How fast the glow pulses
    private static final double MAX_GLOW_ALPHA = 0.9; // Max opacity for the glow
    private static final double MIN_GLOW_ALPHA = 0.3; // Min opacity for the glow

    public Brick(double x, double y, double width, double height, int gridX, int gridY, int maxDurability, String imagePath) {
        super(x, y, width, height);
        this.gridX = gridX;
        this.gridY = gridY;
        this.maxDurability = maxDurability;
        this.currentDurability = maxDurability;

        loadTexture(imagePath);
    }

    @Override
    public void update() {
        if (fading) {
            double fadeSpeed = 2.5;
            opacity -= fadeSpeed * 1 / 60.0;
            if (opacity <= 0) {
                opacity = 0;
                fading = false;
            }
        }
    }

    protected void loadTexture(String imagePath) {
        try {
            textureImage = new Image(imagePath, width, height, false, true);
        } catch (Exception e) {
            System.err.printf("Failed to load image at path: %s. Falling back to color rendering.\n", imagePath);
            this.textureImage = null;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (opacity <= 0) return;

        gc.setGlobalAlpha(opacity);

        if (textureImage != null) {
            gc.drawImage(textureImage, x, y, width, height);
        } else {
            gc.setFill(Color.RED);
            gc.fillRect(x, y, width, height);
        }

        gc.setGlobalAlpha(1.0); // reset lại
        renderGlow(gc);
    }

    protected abstract Color getGlowColor();

    protected void renderGlow(GraphicsContext gc) {
        if (isBroken) return;

        // --- 1. Calculate the Pulsing Alpha ---
        double wave = Math.sin(pulseTimer * PULSE_SPEED) * 0.5 + 0.5; // Wave is 0.0 to 1.0
        double currentGlowAlpha = MIN_GLOW_ALPHA + wave * (MAX_GLOW_ALPHA - MIN_GLOW_ALPHA);

        // --- 2. Get Specific Brick Color ---
        Color glowColor = getGlowColor();

        gc.save();

        // --- Layer 1: Outer soft glow (Wide and Faint) ---
        gc.setGlobalAlpha(currentGlowAlpha * 0.3); // Faintest
        gc.setStroke(glowColor);
        gc.setLineWidth(8.0); // Widest stroke
        gc.strokeRect(this.x, this.y, width, height);

        // --- Layer 2: Medium glow (Medium Brightness) ---
        gc.setGlobalAlpha(currentGlowAlpha * 0.6);
        gc.setStroke(glowColor.brighter());
        gc.setLineWidth(5.0);
        gc.strokeRect(this.x, this.y, width, height);

        // --- Layer 3: Inner core glow (Narrow and Brightest) ---
        gc.setGlobalAlpha(currentGlowAlpha);
        gc.setStroke(Color.WHEAT.darker()); // Use white for the core glow for intensity
        gc.setLineWidth(2.0);
        gc.strokeRect(this.x, this.y, width, height);

        gc.restore();
    }

    @Override
    public boolean takeHit() {
        if (!isBroken && currentDurability > 0) {
            currentDurability--;

            if (currentDurability <= 0) {
                isBroken = true;
                fading = true; // bắt đầu fade
                return true;
            }
            updateAppearance();
        }
        return false;
    }

    protected abstract void updateAppearance();

    protected void updateTexture(String imagePath) {
        loadTexture(imagePath);
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setFading(boolean fading) { this.fading = fading; }

    public double getOpacity() {
        return opacity;
    }

    public void setBroken(boolean broken) { this.isBroken = broken; }

    public List<int[]> triggerSpecialAction() {
        return new ArrayList<>();
    }
}