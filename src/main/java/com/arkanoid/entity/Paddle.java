package com.arkanoid.entity;


import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Paddle extends MovableObject {
    private Image smallPaddleImage ;
    private Image largePaddleImage;

    public String PaddleType;
    public double speed;

    public boolean MultiPowerupInEffect;
    public boolean ExpandPowerupInEffect;
    public boolean FirePowerupInEffect;
    public boolean slowPowerupInEffect;
    public boolean ImmortalPowerupInEffect;

    public Paddle(double x, double y, int width, int height, double dx, double dy,String PaddleType, double speed) {
        super(x, y, width, height, dx, dy);
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

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public boolean takeHit() {
        return false;
    }

    @Override
    public void move() {

    }
}
