package com.arkanoid.entity.brick;

 import javafx.scene.paint.Color;

 public class StrongBrick extends Brick {
     private static final int MAX_DURABILITY = 3;
     private static final String STRONG_BRICK_IMAGE_FULL = "/assets/Bricks/StrongBrick/StrongFull.png";
     private static final String STRONG_BRICK_IMAGE_CRACKED = "/assets/Bricks/StrongBrick/StrongCracked.png";
     private static final String STRONG_BRICK_IMAGE_BROKEN = "/assets/Bricks/StrongBrick/StrongBroken.png";

     public StrongBrick(double x, double y, double width, double height, int gridX, int gridY) {
         super(x, y, width, height, gridX, gridY, 3, STRONG_BRICK_IMAGE_FULL);
         updateAppearance();
     }

     @Override
     protected void updateAppearance() {
         // Change color based on durability.
         if (currentDurability == MAX_DURABILITY - 1) {
             this.updateTexture(STRONG_BRICK_IMAGE_CRACKED);     // Medium: 2
         }
         else if (currentDurability == MAX_DURABILITY - 2) {
             this.updateTexture(STRONG_BRICK_IMAGE_BROKEN);     // Light: 1
         }
     }

     @Override
     protected Color getGlowColor() {
         return Color.STEELBLUE.darker();
     }
 }