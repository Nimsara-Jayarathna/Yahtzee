package Yahtzee.src;

import java.util.Random;
import java.util.Scanner;

public class Dices {
    Scanner sc = new Scanner(System.in);
    private Random random = new Random();
    private int[] dices = new int[5];
    private boolean[] hold = new boolean[5];
    private int rollsRemaining = 3;

    private String[][] diceFaces = {
    { "┌───────┐", "│       │", "│   ●   │", "│       │", "└───────┘" },  // 1
    { "┌───────┐", "│ ●     │", "│       │", "│     ● │", "└───────┘" },  // 2
    { "┌───────┐", "│ ●     │", "│   ●   │", "│     ● │", "└───────┘" },  // 3
    { "┌───────┐", "│ ●   ● │", "│       │", "│ ●   ● │", "└───────┘" },  // 4
    { "┌───────┐", "│ ●   ● │", "│   ●   │", "│ ●   ● │", "└───────┘" },  // 5
    { "┌───────┐", "│ ●   ● │", "│ ●   ● │", "│ ●   ● │", "└───────┘" },  // 6
};

    private int randomNumber(){
        return (random.nextInt(6) + 1);
    }

    public int getDices(int index) {
        return dices[index];
    }

    public int[] getDices(){
        return dices;
    }

    public void rollDice(int index) {
        dices[index] = this.randomNumber();
    }

    public void firstRoll(){
        for (int i = 0; i < 5; i++) {
            dices[i] = this.randomNumber();
        }
    }

    public boolean reroll() {
    Scanner sc = new Scanner(System.in);

    if (rollsRemaining <= 0) {
        System.out.println("No re-rolls remaining!");
        return true;
    }

    System.out.print("Do you want to re-roll? (y/n): ");
    String choice = sc.nextLine().toLowerCase();

    if (choice.equals("y")) {
        boolean validRoll = false;

        while (!validRoll) { // Loop until valid input is received

            System.out.print("Enter dice to re-roll (1-5, separated by spaces): ");
            String input = sc.nextLine();

            String[] rerollIndices = input.split(" ");

            //Check the valid indices
            boolean localValid = true;
             for (String rerollIndex : rerollIndices) {
                try {
                    int index = Integer.parseInt(rerollIndex) - 1;
                    if (index < 0 || index >= 5) { // Check for invalid range
                        System.out.println("Invalid dice number: " + (index + 1));
                        localValid = false;
                        break; // Exit the loop early if invalid dice number
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numbers between 1 and 5");
                    localValid = false;
                    break; // Exit the loop early if there's a number format exception
                }
            }

            if(localValid){
                   //Set all dice to unheld
                    for (int i = 0; i < 5; i++) {
                         hold[i] = false;
                    }
                         for (String rerollIndex : rerollIndices) { // Reroll the dice
                                try {
                                    int index = Integer.parseInt(rerollIndex) - 1;
                                     dices[index] = this.randomNumber();

                                } catch (NumberFormatException e) {

                                    break; // should not reach here
                                }
                            }
                validRoll = true;
            }
             //check the loop and break with valid rolls

            if (validRoll) { // Only roll if input is valid
                   rollsRemaining--; // Decrement rolls remaining only on valid roll!
                System.out.println("Rerolling dice...");

           } else{
                System.out.println("The Roll was unsuccessful try again");
        } // End check
    }
         return false; // Re-roll successful
         }
     else if (choice.equals("n"))
      {
            System.out.println("Skipping re-roll.");
            return true;  // turn is over with choice n to not reroll, no rolls are used
       } else
       {
            System.out.println("Invalid choice. Please enter 'y' or 'n'.");
             return reroll(); // Try again (turn is also not over) with incorrect choice from y/n
      }

}

    public void printDices(){
        for (int row = 0; row < 5; row++) {  // Each dice face has 3 lines
            for (int i = 0; i < 5; i++) {
                System.out.printf("%s  ", diceFaces[dices[i] - 1][row]);
            }
            System.out.println();
        } 
    }


}
