package crorepati;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuizGame {
    private List<Question> questions;
    private int currentScore;
    private boolean lifelineUsed;

    public QuizGame() {
        this.questions = new ArrayList<>();
        this.currentScore = 0;
        this.lifelineUsed = false;
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question("What is the capital of France?", Arrays.asList("1. Berlin", "2. Madrid", "3. Paris", "4. Rome"), 2));
        questions.add(new Question("Which planet is known as the Red Planet?", Arrays.asList("1. Earth", "2. Mars", "3. Jupiter", "4. Saturn"), 1));
        questions.add(new Question("What is the largest ocean on Earth?", Arrays.asList("1. Atlantic Ocean", "2. Indian Ocean", "3. Arctic Ocean", "4. Pacific Ocean"), 3));
        questions.add(new Question("Who wrote 'To Kill a Mockingbird'?", Arrays.asList("1. Harper Lee", "2. J.K. Rowling", "3. Ernest Hemingway", "4. Mark Twain"), 0));
        questions.add(new Question("What is the chemical symbol for Gold?", Arrays.asList("1. Au", "2. Ag", "3. Pb", "4. Fe"), 0));
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Crorepati!");
        System.out.println("Answer the questions to win points. You have one 50-50 lifeline.");
        
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.getQuestionText());
            
            List<String> currentOptions = new ArrayList<>(q.getOptions());
            displayOptions(currentOptions);
            
            long startTime = System.currentTimeMillis();
            boolean answered = false;

            while (!answered) {
                System.out.print("Enter your choice (1-4) or 'L' for 50-50 lifeline: ");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.equals("L")) {
                    if (!lifelineUsed) {
                        useLifeline(currentOptions, q.getCorrectAnswerIndex());
                        lifelineUsed = true;
                        System.out.println("Lifeline used! Here are the remaining options:");
                        displayOptions(currentOptions);
                    } else {
                        System.out.println("You have already used your lifeline!");
                    }
                } else {
                    try {
                        int choice = Integer.parseInt(input) - 1;
                        long endTime = System.currentTimeMillis();
                        long timeTaken = (endTime - startTime) / 1000;
                        
                        if (choice == q.getCorrectAnswerIndex()) {
                            System.out.println("Correct! You took " + timeTaken + " seconds.");
                            currentScore += 1000;
                        } else {
                            System.out.println("Incorrect! The correct answer was " + q.getOptions().get(q.getCorrectAnswerIndex()));
                        }
                        answered = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    }
                }
            }
        }
        
        System.out.println("\nGame Over!");
        System.out.println("Your final score is: " + currentScore);
        if (currentScore == questions.size() * 1000) {
            System.out.println("Excellent performance! You're a Crorepati!");
        } else if (currentScore > 0) {
            System.out.println("Good job! Keep practicing.");
        } else {
            System.out.println("Better luck next time.");
        }
        
        scanner.close();
    }

    private void displayOptions(List<String> options) {
        for (String option : options) {
            if (!option.isEmpty()) {
                System.out.println(option);
            }
        }
    }

    private void useLifeline(List<String> options, int correctIndex) {
        int removed = 0;
        for (int i = 0; i < options.size(); i++) {
            if (i != correctIndex && removed < 2) {
                options.set(i, "");
                removed++;
            }
        }
    }
}
