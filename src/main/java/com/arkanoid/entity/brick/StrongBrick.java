package com.arkanoid.entity.brick;

 import javafx.scene.paint.Color;

 public class StrongBrick extends Brick {
     private static final int MAX_DURABILITY = 3;
     private static final String STRONG_BRICK_IMAGE = "/assets/gameItems/StrongBrick/Strong1.png";

     public StrongBrick(double x, double y, double width, double height, int gridX, int gridY) {
         super(x, y, width, height, gridX, gridY, 3, STRONG_BRICK_IMAGE);
         updateAppearance();
     }

     @Override
     protected void updateAppearance() {
         // Change color based on durability.
         if (currentDurability == MAX_DURABILITY - 1) {
             this.updateTexture(STRONG_BRICK_IMAGE);     // Medium: 2
         }
         else if (currentDurability == MAX_DURABILITY - 2) {
             this.updateTexture(STRONG_BRICK_IMAGE);     // Light: 1
         }
     }
 }