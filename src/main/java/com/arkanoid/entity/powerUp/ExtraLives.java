package com.arkanoid.entity.powerUp;

import com.arkanoid.ui.Lives;

public abstract class ExtraLives  {

    protected Lives livesUI;

    public ExtraLives(Lives livesUI) {
        this.livesUI = new Lives();
    }
    public void activate() {
        livesUI.gainLives(); // hoặc livesUI.setLives(livesUI.getLives() + 1);
    }
}
