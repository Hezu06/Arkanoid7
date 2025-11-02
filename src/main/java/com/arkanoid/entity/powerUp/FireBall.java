package com.arkanoid.entity.powerUp;

import com.arkanoid.entity.Ball;
import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

class FireBall extends PowerUp {
    public FireBall(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/fire.png"))));
    }
    @Override
    public void applyEffect(GameMain game) {
        Image ballImg = new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("/assets/Ball/fireBall.png")));
        ImageView ballImageView = new ImageView(ballImg);
        for (Ball ball : game.getListBalls()) {
            Ball.setImage(ballImageView.getImage());
            ball.activateFireBall();
        }
    }
}