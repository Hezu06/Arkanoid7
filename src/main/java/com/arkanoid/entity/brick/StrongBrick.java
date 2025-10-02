package com.arkanoid.entity.brick;

 import javafx.scene.paint.Color;

 public class StrongBrick extends Brick {
     private static final int MAX_DURABILITY = 3;

     public StrongBrick(double x, double y, double width, double height, int gridX, int gridY) {
         super(x, y, width, height, gridX, gridY, 3, Color.ALICEBLUE);
         updateAppearance();
     }

     @Override
     protected void updateAppearance() {
         // Change color based on durability.
         if (currentDurability == MAX_DURABILITY - 1) {
             this.color = Color.AQUA;     // Medium: 2
         }
         else if (currentDurability == MAX_DURABILITY - 2) {
             this.color = Color.AZURE;     // Light: 1
         }
     }
 }