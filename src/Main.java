package Yahtzee.src;

import Yahtzee.src.Dices;
import Yahtzee.src.Rules;
import Yahtzee.src.Player;
// import Yahtzee.src.AIPlayer; // This import is no longer needed
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dices AIDice = new Dices();
        Dices HumanDice = new Dices();
        Player AIPlayer = new Player();
        Player HumanPlayer = new Player();
        Rules rules = new Rules();
        YahtzeeAI ai = new YahtzeeAI(); // Create an instance of our new AI class

        System.out.print("Enter the name of the human Player: ");
        HumanPlayer.setPlayerName(sc.nextLine());
        HumanPlayer.initialize();
        System.out.print("Enter the name of the AI Player: ");
        AIPlayer.setPlayerName(sc.nextLine());
        AIPlayer.initialize();

        for (int i = 0; i < 13; i++) {
            System.out.println("------------------------------------------");
            System.out.println("Round " + (i + 1));
            System.out.println("------------------------------------------");

            // --- Human Player's Turn ---
            System.out.println("--- " + HumanPlayer.getPlayerName() + "'s Turn ---");
            HumanDice.resetTurn();
            HumanDice.firstRoll();
            System.out.println("Initial Roll:");
            HumanDice.printDices();

            for (int ii = 0; ii < 2; ii++){
                // The reroll method returns true if the turn is over (player chose 'n')
                if (HumanDice.reroll()){
                    break;
                }
                System.out.println("Roll " + (ii + 2) + ":");
                HumanDice.printDices();
            }

            HumanPlayer.printAvailableComb();
            System.out.print("Enter the combination number to score: ");
            int index = sc.nextInt(); sc.nextLine();
            while(index < 1 || index > 13 || !(HumanPlayer.setScore((index - 1), rules.getScore((index - 1), HumanDice.getDices())))){
                HumanPlayer.printAvailableComb();
                System.out.print("Invalid combination! Please enter again: ");
                index = sc.nextInt(); sc.nextLine();
            }
            System.out.println(HumanPlayer.getPlayerName() + "'s scores after turn:");
            HumanPlayer.printScores();
            System.out.printf("\n");


            // --- AI Player's Turn (New Logic) ---
            System.out.println("--- " + AIPlayer.getPlayerName() + "'s Turn ---");
            AIDice.firstRoll();
            System.out.println("Initial Roll:");
            AIDice.printDices();

            // The AI takes its entire turn, including re-rolls and final scoring
            ai.takeTurn(AIDice, AIPlayer, i);

            System.out.println(AIPlayer.getPlayerName() + "'s scores after turn:");
            AIPlayer.printScores();
            System.out.printf("\n");
        }

        // --- Final Score Display ---
        System.out.println("==========================================");
        System.out.println("FINAL SCORES");
        System.out.println("==========================================");
        System.out.println(HumanPlayer.getPlayerName() + ":");
        HumanPlayer.printScores();
        System.out.printf("\n");
        System.out.println(AIPlayer.getPlayerName() + ":");
        AIPlayer.printScores();
    }
}