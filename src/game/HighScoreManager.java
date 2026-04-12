package game;

import java.io.*;
import java.util.*;

public class HighScoreManager {

    private static final String FILE_NAME = "highscores.txt";

    public static void saveScore(String name, int score) { // Saves the top 10 scores
        List<ScoreEntry> scores = loadScores();
        scores.add(new ScoreEntry(name, score));

        // sort descending
        scores.sort((a, b) -> Integer.compare(b.score, a.score));

        // keep only top 10
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }

        saveScoresToFile(scores);
    }

    public static List<ScoreEntry> loadScores() { // Reads the scores from the file and returns them
        List<ScoreEntry> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                scores.add(new ScoreEntry(name, score));
            }
        } catch (IOException ignored) {}

        return scores;
    }

    private static void saveScoresToFile(List<ScoreEntry> scores) { //
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (ScoreEntry entry : scores) {
                fw.write(entry.name + "," + entry.score + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ScoreEntry {
        public String name;
        public int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
