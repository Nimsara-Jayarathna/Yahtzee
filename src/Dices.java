package Yahtzee.src;

import java.util.Random;

public class Dices {
    private Random random = new Random();
    private int[] dices = new int[5];

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
    public void printDices(){
        for (int row = 0; row < 5; row++) {  // Each dice face has 3 lines
            for (int i = 0; i < 5; i++) {
                System.out.printf("%s  ", diceFaces[dices[i] - 1][row]);
            }
            System.out.println();
        } 
    }


}
