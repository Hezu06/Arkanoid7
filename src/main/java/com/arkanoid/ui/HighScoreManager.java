package com.arkanoid.ui;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HighScoreManager {

    private static final String SCORE_FILE = "scores.txt";
    private static final int MAX_SCORES_DISPLAY = 10;

    private static HighScoreManager instance = null;

    public static HighScoreManager getInstance() {
        if (instance == null) {
            instance = new HighScoreManager();
        }
        return instance;
    }

    public void saveScore(int score) {
        try (PrintWriter scoreWriter = new PrintWriter(new FileWriter(SCORE_FILE, true))) {
            scoreWriter.println(score);
            System.out.println("Score saved: " + score);
        }
        catch (IOException e) {
            System.err.println("Cannot save score: " + e.getMessage());
        }
    }

    public List<Integer> getTopScores() {
        List<Integer> allScores = loadScores();

        allScores.sort(Comparator.reverseOrder());

        return allScores.stream()
                .limit(MAX_SCORES_DISPLAY)
                .collect(Collectors.toList());
    }

    private List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();

        try (BufferedReader scoreReader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = scoreReader.readLine()) != null) {
                try {
                    int score = Integer.parseInt(line);
                    scores.add(score);
                }
                catch (NumberFormatException e) {
                    System.err.println("Invalid score format: " + line);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return scores;
    }
}
