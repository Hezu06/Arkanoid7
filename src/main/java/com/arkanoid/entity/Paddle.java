package com.arkanoid.entity;


import java.awt.*;

public class Paddle {
    private Image smallPaddleImage ;
    private Image largePaddleImage;

    public double x;
    public double y;
    public String PaddleType;
    public double speed;

    public boolean MultiPowerupInEffect;
    public boolean ExpandPowerupInEffect;
    public boolean FirePowerupInEffect;
    public boolean slowPowerupInEffect;
    public boolean ImmortalPowerupInEffect;

    public Paddle(double x, double y, String PaddleType, double speed) {
        this.x = x;
        this.y = y;
        this.PaddleType = PaddleType;
        this.speed = speed;

        MultiPowerupInEffect = false;
        ExpandPowerupInEffect = false;
        MultiPowerupInEffect = false;
        FirePowerupInEffect = false;
        slowPowerupInEffect = false;
        ImmortalPowerupInEffect = false;

//        public Image getImage() {
//            if (PaddleType.equals("large")) {
//                return largePaddleImage;
//            }
//            return smallPaddleImage;
//        }
    }

    public void removeAllPowerupEffects() {
        MultiPowerupInEffect = false;
        ExpandPowerupInEffect = false;
        slowPowerupInEffect = false;
        FirePowerupInEffect = false;
        ImmortalPowerupInEffect = false;
        PaddleType = "small";
    }
}
