package com.arkanoid.ui;
import java.io.*;
import java.util.*;


public class Point extends Lives{
    private static final String SCORE_FILE = "src/score.txt";
    private int point;
    public Point() {
        this.point = loadPoint();
    }
    private int loadPoint() {
        try (BufferedReader br = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line = br.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Chưa có điểm cũ, bắt đầu từ 0.");
        }
        return 0;
    }

    // Lưu điểm xuống file
    private void saveScore() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            bw.write(String.valueOf(point));
        } catch (IOException e) {
            System.out.println("Không thể lưu điểm: " + e.getMessage());
        }
    }
}


