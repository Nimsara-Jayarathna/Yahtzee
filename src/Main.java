package Yahtzee.src;

import Yahtzee.src.Dices;
import Yahtzee.src.Rules;
import Yahtzee.src.Player;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dices AIDice = new Dices();
        Dices HumanDice = new Dices();
        Player AIPlayer = new Player();
        Player HumanPlayer = new Player();
        Rules rules = new Rules();
        System.out.print("Enter the name of the human Player: ");
        HumanPlayer.setPlayerName(sc.nextLine());
        HumanPlayer.initialize();
        System.out.print("Enter the name of the AI Player: ");
        AIPlayer.setPlayerName(sc.nextLine());
        AIPlayer.initialize();

        for (int i = 0; i < 13; i++) {
            System.out.println("Roll No - " + (i + 1));
            HumanDice.firstRoll();
            HumanDice.printDices();
            for (int ii = 0; ii < 2; ii++){
                if (HumanDice.reroll()){
                    HumanDice.printDices();
                    break;
                } else {
                    HumanDice.printDices();
                };
            }
            HumanPlayer.printAvailableComb();
            System.out.print("Enter the combination: ");
            int index = sc.nextInt(); sc.nextLine();
            while(index < 1 || index > 13 || !(HumanPlayer.setScore((index - 1), rules.getScore((index - 1), HumanDice.getDices())))){
                HumanPlayer.printAvailableComb();
                System.out.print("Invalid combinatio! Please enter again: ");
                index = sc.nextInt(); sc.nextLine();
            }
            HumanPlayer.printScores();
            System.out.printf("\n");
            AIDice.firstRoll();
            AIDice.printDices();
            AIPlayer.setScore(i, rules.getScore(i, AIDice.getDices()));
            AIPlayer.printScores();
            System.out.printf("\n");
        }

        HumanPlayer.printScores();
        System.out.printf("\n");
        AIPlayer.printScores();

    }
}