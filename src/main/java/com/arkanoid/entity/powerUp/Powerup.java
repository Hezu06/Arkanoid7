package com.arkanoid.entity.powerUp;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class Powerup {
    //Images for the all the Powerups
    private Image MultiImage;
    private Image ExpandImage;
    private Image slowImage;
    private Image fireImage;

    public double x;
    public double y;
    public String powerupType; //type of Powerup ("catch", "duplicate", ...)
    public double width = 31; //width of the Powerup Rect
    public double height = 16; //height of the Powerup Rect
    public double fallingSpeed; //falling speed of the Powerup
    public boolean isRemoved; //determines if Powerup is removed

    //CONSTRUCTOR
    public Powerup(double x, double y, double fallingSpeed) {
        this.x = x;
        this.y = y;
        this.fallingSpeed = fallingSpeed;
        //randomly generate the Powerup type
        String[] powerupTypes = { "multi", "expand", "slow", "fire"};
        Random rand = new Random();
        powerupType = powerupTypes[rand.nextInt(powerupTypes.length)];
        isRemoved = false;

    }

    //fall() moves the Powerup down the screen and removes it if
    //it passes the y end boundary
    public void fall(double yEndBoundary) {
        y += fallingSpeed;
        if (y >= yEndBoundary) {
            isRemoved = true;
        }
    }
}
