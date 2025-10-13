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
    public void play() {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        boolean playing = true;
        System.out.println("Điểm hiện tại: " + point);

        while (playing) {
            int target = rand.nextInt(10) + 1; // số từ 1-10
            System.out.print("Đoán số (1-10): ");
            int guess = sc.nextInt();

            if (guess == target) {
                System.out.println("Chính xác! +10 điểm");
                point += 10;
            } else {
                System.out.println("Sai rồi! Số đúng là: " + target);
                point -= 5;
            }

            System.out.println("Điểm hiện tại: " + point);
            System.out.println("Mạng hiện tại:"+lives);

            System.out.print("Chơi tiếp? (y/n): ");
            String choice = sc.next();
            if (choice.equalsIgnoreCase("n")) {
                playing = false;
            }else if(lives<=0){
                playing=false;
            }
        }

        saveScore();
        System.out.println("Điểm đã lưu lại! Điểm cuối cùng: " + point);
    }
}

