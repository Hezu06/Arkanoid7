package com.arkanoid.ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {
    private MediaPlayer mediaPlayer;
    String soundFile = "/sounds/background_sound.mp3";

    public SoundManager() {};
    public SoundManager(String soundFile) {
        this.soundFile = soundFile;
    }

    public void playBackgroundMusic() {
        if(mediaPlayer == null) {
            try {
                // Lấy URL của file tài nguyên
                URL resource = getClass().getResource(soundFile);

                if (resource == null) {
                    System.err.println("Không thể tìm thấy file nhạc: " + soundFile);
                    return;
                }

                // Tạo Media và MediaPlayer
                Media media = new Media(resource.toExternalForm());
                mediaPlayer = new MediaPlayer(media);

                // Cài đặt để nhạc lặp lại vô tận
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                // Phát nhạc
                mediaPlayer.play();

            } catch (Exception e) {
                System.err.println("Lỗi khi phát nhạc:");
                e.printStackTrace();
            }
        }

        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            // volume là giá trị từ 0.0 đến 1.0
            mediaPlayer.setVolume(volume);
        }
    }
}
