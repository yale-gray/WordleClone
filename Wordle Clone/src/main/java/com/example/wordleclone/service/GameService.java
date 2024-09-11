package com.example.wordleclone.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private static final int MAX_ATTEMPTS = 6;
    private static final int WORD_LENGTH = 5;
    private List<String> words = Arrays.asList("apple", "grape", "pearl", "stone", "crane", "chair");

    private String wordToGuess;
    private int attempts;
    private boolean gameWon;

    public void startNewGame() {
        Random random = new Random();
        wordToGuess = words.get(random.nextInt(words.size()));
        attempts = 0;
        gameWon = false;
    }

    public Feedback processGuess(String playerGuess) {
        attempts++;
        boolean gameOver = attempts >= MAX_ATTEMPTS;

        if (playerGuess.equals(wordToGuess)) {
            gameWon = true;
            return new Feedback(playerGuess, "GGGGG", true, gameOver);
        }

        char[] feedback = new char[WORD_LENGTH];
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (playerGuess.charAt(i) == wordToGuess.charAt(i)) {
                feedback[i] = 'G'; // Correct letter and position
            } else if (wordToGuess.contains(String.valueOf(playerGuess.charAt(i)))) {
                feedback[i] = 'Y'; // Correct letter, wrong position
            } else {
                feedback[i] = 'B'; // Incorrect letter
            }
        }

        return new Feedback(playerGuess, new String(feedback), false, gameOver);
    }

    public static class Feedback {
        private String guess;
        private String feedback;
        private boolean gameWon;
        private boolean gameOver;

        public Feedback(String guess, String feedback, boolean gameWon, boolean gameOver) {
            this.guess = guess;
            this.feedback = feedback;
            this.gameWon = gameWon;
            this.gameOver = gameOver;
        }

        public String getGuess() {
            return guess;
        }

        public String getFeedback() {
            return feedback;
        }

        public boolean isGameWon() {
            return gameWon;
        }

        public boolean isGameOver() {
            return gameOver;
        }
    }
}
