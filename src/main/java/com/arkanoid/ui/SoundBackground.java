package com.arkanoid.ui;

public class SoundBackground {
    private static SoundManager soundManager;
    private SoundBackground() {};
    public static SoundManager getInstance() {
        if(soundManager == null) {
            soundManager = new SoundManager("/sounds/background_music.mp3");
        }
        return soundManager;
    }
}
