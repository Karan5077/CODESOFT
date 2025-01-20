
import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    private static final int MAX_TRIES = 10;
    private int numberToGuess;
    private int tries;

    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame();
        game.startGame();
    }

    public void startGame() {
        initializeGame();
        boolean gameWon = false;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I have selected a number between 1 and 100. You have " + MAX_TRIES + " tries to guess it.");

        while (tries < MAX_TRIES) {
            int userGuess = getUserGuess();
            if (isCorrectGuess(userGuess)) {
                System.out.println("Congratulations! You guessed the number in " + (tries + 1) + " tries.");
                gameWon = true;
                break;
            } else {
                giveFeedback(userGuess);
            }
        }

        if (!gameWon) {
            System.out.println("Sorry! You've used all your tries. The correct number was " + numberToGuess + ".");
        }
    }

    private void initializeGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        tries = 0;
    }

    private int getUserGuess() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your guess: ");
        int guess = scanner.nextInt();
        tries++;
        return guess;
    }

    private boolean isCorrectGuess(int guess) {
        return guess == numberToGuess;
    }

    private void giveFeedback(int guess) {
        if (guess < numberToGuess) {
            System.out.println("Too low! Try again.");
        } else {
            System.out.println("Too high! Try again.");
        }
    }
}
