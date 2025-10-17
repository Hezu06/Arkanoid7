package com.arkanoid.ui;

public class Lives {
     public int lives;

    // Constructor (phải có dấu ngoặc tròn sau tên class)
    public Lives() {
        this.lives = 3;
    }

    // Giảm mạng
    public void loseLives() {
        this.lives -= 1;
        if (this.lives < 0) this.lives = 0;
    }

    // Tăng mạng
    public void gainLives() {
        this.lives += 1;
    }

    // Getter để xem còn bao nhiêu mạng
    public int getLives() {
        return this.lives;
    }
}

