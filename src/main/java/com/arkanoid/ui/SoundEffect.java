package com.arkanoid.ui;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class SoundEffect {

    private static AudioClip buttonClickSound;
    private static double CLICK_VOLUME = 0.5;

    public static void load() {
        try {
            String soundFile = "/sounds/click_sound.mp3"; // Đổi tên file
            URL resource = SoundEffect.class.getResource(soundFile);

            if (resource != null) {
                buttonClickSound = new AudioClip(resource.toExternalForm());
                buttonClickSound.setVolume(CLICK_VOLUME);
            } else {
                System.err.println("Không tìm thấy file SFX: " + soundFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playButtonClick() {
        if (buttonClickSound != null) {
            buttonClickSound.play();
        }
    }
}
