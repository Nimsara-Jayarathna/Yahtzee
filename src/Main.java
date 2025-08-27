package Yahtzee.src;

import Yahtzee.src.Dices;
import Yahtzee.src.Rules;
import Yahtzee.src.Player;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    // ANSI Escape Codes for colors to make the UI more vibrant
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BOLD = "\u001B[1m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dices AIDice = new Dices();
        Dices HumanDice = new Dices();
        Player AIPlayer = new Player();
        Player HumanPlayer = new Player();
        Rules rules = new Rules();
        YahtzeeAI ai = new YahtzeeAI(); // Create an instance of our new AI class

        printWelcomeBanner();

        System.out.print(ANSI_YELLOW + "Enter your name: " + ANSI_RESET);
        HumanPlayer.setPlayerName(sc.nextLine());
        HumanPlayer.initialize();

        System.out.print(ANSI_YELLOW + "Enter a name for your AI opponent: " + ANSI_RESET);
        AIPlayer.setPlayerName(sc.nextLine());
        AIPlayer.initialize();

        for (int i = 0; i < 13; i++) {
            clearScreen();
            System.out.println(ANSI_PURPLE + ANSI_BOLD + "======================================================");
            System.out.println("                     ROUND " + (i + 1) + " of 13");
            System.out.println("======================================================" + ANSI_RESET);

            // --- Human Player's Turn ---
            System.out.println("\n--- " + ANSI_CYAN + HumanPlayer.getPlayerName() + "'s Turn" + ANSI_RESET + " ---");
            HumanDice.resetTurn();
            HumanDice.firstRoll();
            System.out.println("Initial Roll:");
            HumanDice.printDices();
            displayScoreboard(HumanPlayer, AIPlayer);

            for (int ii = 0; ii < 2; ii++){
                if (HumanDice.reroll()){
                    break;
                }
                System.out.println("\nRoll " + (ii + 2) + ":");
                HumanDice.printDices();
                displayScoreboard(HumanPlayer, AIPlayer);
            }

            HumanPlayer.printAvailableComb();
            System.out.print(ANSI_YELLOW + "\nEnter the combination number to score: " + ANSI_RESET);
            int indexChoice = sc.nextInt(); sc.nextLine();
            int scoreGained = rules.getScore(indexChoice - 1, HumanDice.getDices());

            while(indexChoice < 1 || indexChoice > 13 || !(HumanPlayer.setScore((indexChoice - 1), scoreGained))){
                HumanPlayer.printAvailableComb();
                System.out.print(ANSI_YELLOW + "Invalid or used combination! Please enter again: " + ANSI_RESET);
                indexChoice = sc.nextInt(); sc.nextLine();
                scoreGained = rules.getScore(indexChoice - 1, HumanDice.getDices());
            }

            System.out.println("\n" + ANSI_GREEN + ANSI_BOLD + "You scored " + scoreGained + " points for " + Player.COMBINATIONS[indexChoice - 1] + "!" + ANSI_RESET);
            displayScoreboard(HumanPlayer, AIPlayer);

            promptEnterKey();


            // --- AI Player's Turn ---
            clearScreen();
            System.out.println(ANSI_PURPLE + ANSI_BOLD + "======================================================");
            System.out.println("                     ROUND " + (i + 1) + " of 13");
            System.out.println("======================================================" + ANSI_RESET);
            System.out.println("\n--- " + ANSI_GREEN + AIPlayer.getPlayerName() + "'s Turn" + ANSI_RESET + " ---");
            
            AIDice.resetTurn();
            AIDice.firstRoll();
            System.out.println("Initial Roll:");
            AIDice.printDices();
            displayScoreboard(HumanPlayer, AIPlayer);
            waitFor(2); 

            ai.takeTurn(AIDice, AIPlayer, i);
            waitFor(1);

            System.out.println("\n" + ANSI_GREEN + AIPlayer.getPlayerName() + "'s scores after turn:" + ANSI_RESET);
            displayScoreboard(HumanPlayer, AIPlayer);
            
            if (i < 12) { 
                promptEnterKey();
            }
        }

        // --- Final Score Display ---
        printFinalScores(HumanPlayer, AIPlayer);
    }

    /**
     * Displays a detailed, side-by-side scoreboard for both players.
     */
    public static void displayScoreboard(Player human, Player ai) {
        System.out.println(ANSI_YELLOW + "\n" + ANSI_BOLD + "====================== SCOREBOARD ======================" + ANSI_RESET);
        // Use shorter names if they are long to prevent formatting issues
        String humanName = human.getPlayerName().length() > 12 ? human.getPlayerName().substring(0, 11) + "." : human.getPlayerName();
        String aiName = ai.getPlayerName().length() > 12 ? ai.getPlayerName().substring(0, 11) + "." : ai.getPlayerName();
        System.out.printf(ANSI_YELLOW + "%-18s | %-12s | %-12s\n" + ANSI_RESET, "Combination", humanName, aiName);
        System.out.println(ANSI_YELLOW + "------------------------------------------------------" + ANSI_RESET);

        // Upper Section
        for (int i = 0; i < 6; i++) {
            System.out.printf("%-18s | %-12s | %-12s\n", Player.COMBINATIONS[i],
                    human.getCombinationUsed(i) ? human.getScore(i) : "-",
                    ai.getCombinationUsed(i) ? ai.getScore(i) : "-");
        }
        System.out.println(ANSI_YELLOW + "------------------------------------------------------" + ANSI_RESET);
        System.out.printf(ANSI_BOLD + "%-18s | %-12d | %-12d\n" + ANSI_RESET, "UPPER SCORE", human.getUpperScore(), ai.getUpperScore());
        System.out.printf(ANSI_BOLD + "%-18s | %-12d | %-12d\n" + ANSI_RESET, "BONUS (if > 63)", human.getBonusScore(), ai.getBonusScore());
        System.out.println(ANSI_YELLOW + "------------------------------------------------------" + ANSI_RESET);

        // Lower Section
        for (int i = 6; i < 13; i++) {
            System.out.printf("%-18s | %-12s | %-12s\n", Player.COMBINATIONS[i],
                    human.getCombinationUsed(i) ? human.getScore(i) : "-",
                    ai.getCombinationUsed(i) ? ai.getScore(i) : "-");
        }
        System.out.println(ANSI_YELLOW + "------------------------------------------------------" + ANSI_RESET);
        System.out.printf(ANSI_BOLD + "%-18s | %-12d | %-12d\n" + ANSI_RESET, "LOWER SCORE", human.getLowerScore(), ai.getLowerScore());
        System.out.println(ANSI_YELLOW + "------------------------------------------------------" + ANSI_RESET);

        // Total Score
        int humanTotal = human.getTotalScore() + human.getBonusScore();
        int aiTotal = ai.getTotalScore() + ai.getBonusScore();
        System.out.printf(ANSI_GREEN + ANSI_BOLD + "%-18s | %-12d | %-12d\n" + ANSI_RESET, "GRAND TOTAL", humanTotal, aiTotal);
        System.out.println(ANSI_YELLOW + "======================================================\n" + ANSI_RESET);
    }

    public static void printWelcomeBanner() {
        clearScreen();
        System.out.println(ANSI_PURPLE + ANSI_BOLD);
        System.out.println("******************************************");
        System.out.println("*                                        *");
        System.out.println("*          WELCOME TO YAHTZEE!           *");
        System.out.println("*                                        *");
        System.out.println("******************************************");
        System.out.println(ANSI_RESET);
    }

    public static void printFinalScores(Player human, Player ai) {
        clearScreen();
        System.out.println(ANSI_PURPLE + ANSI_BOLD + "======================================================");
        System.out.println("                      GAME OVER                 ");
        System.out.println("======================================================" + ANSI_RESET);

        displayScoreboard(human, ai);

        int humanTotal = human.getTotalScore() + human.getBonusScore();
        int aiTotal = ai.getTotalScore() + ai.getBonusScore();

        System.out.println(ANSI_YELLOW + "\n------------------------------------------------------" + ANSI_RESET);
        if (humanTotal > aiTotal) {
            System.out.println(ANSI_BOLD + ANSI_CYAN + "CONGRATULATIONS, " + human.getPlayerName() + "! YOU ARE THE WINNER!" + ANSI_RESET);
        } else if (aiTotal > humanTotal) {
            System.out.println(ANSI_BOLD + ANSI_GREEN + ai.getPlayerName() + " IS THE WINNER! Better luck next time." + ANSI_RESET);
        } else {
            System.out.println(ANSI_BOLD + ANSI_YELLOW + "IT'S A TIE!" + ANSI_RESET);
        }
        System.out.println(ANSI_YELLOW + "------------------------------------------------------" + ANSI_RESET);
    }
    
    public static void promptEnterKey(){
        System.out.print(ANSI_YELLOW + "\nPress Enter to continue..." + ANSI_RESET);
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }
}