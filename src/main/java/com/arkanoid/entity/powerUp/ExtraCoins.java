package com.arkanoid.entity.powerUp;

import com.arkanoid.game.GameMain;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

public class ExtraCoins extends PowerUp {

    public ExtraCoins(double x, double y) {
        super(x, y, 32, 32);
        super.setImage(new Image(Objects.requireNonNull(getClass().getClassLoader().
                getResourceAsStream("powerUpItem/bigPurple.png"))));
    }

    @Override
    protected Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public void applyEffect(GameMain game) {
        game.getGameStateManager().addScoreForCoinPowerUp();

        System.out.println("+1 coins!");
    }
}