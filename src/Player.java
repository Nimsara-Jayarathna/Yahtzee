package Yahtzee.src;

public class Player {
    private String name;
    private int[] scores = new int[13];
    private boolean[] used = new boolean[13];
    public static final String[] COMBINATIONS = {
            "Ones",
            "Twos",
            "Threes",
            "Fours",
            "Fives",
            "Sixes",
            "Three of a Kind",
            "Four of a Kind",
            "Full House",
            "Small Straight",
            "Large Straight",
            "Yahtzee",
            "Chance"
    };

    public void printAvailableComb() {
        System.out.println("Available combinations are: ");
        for (int i = 0; i < 13; i++) {
            if(used[i] == false) {
                System.out.println((i + 1) + ". " + COMBINATIONS[i]);
            }
        }
    }

    public void setPlayerName(String name){
        this.name = name; // initialize the player with its name mutators
    }
    public String getPlayerName(){
        return name;    // getter method for name accessors
    }

    public boolean initialize(){
        for (int i = 0; i < 13; i++) {
            scores[i] = 0;
            used[i] = false;
        }
        for (int i = 0; i < 13; i++) {
            if ((used[i] != false) || (scores[i] != 0)){
                return false;
            }
        }
        return true;
    }

    public boolean setScore(int combinationNo, int score) {
        if (setCombinationUsed(combinationNo) == true) {
            scores[combinationNo] = score;
            return true;
        } else {
            return false;
        }
    }
    public int getScore(int combinationNo) {
        return scores[combinationNo];
    }

    private boolean setCombinationUsed(int combinationNo) {
        if (used[combinationNo] == true) {
            return false;
        }
        used[combinationNo] = true;
        return true;
    }
    public boolean getCombinationUsed(int combinationNo){
        return used[combinationNo];
    }

    public int getUpperScore(){
        int score = 0;
        for (int i = 0; i < 6; i++){
            score += scores[i];
        }
        return score;
    }
    public int getLowerScore(){
        int score = 0;
        for (int i = 6; i < 13; i++){
            score += scores[i];
        }
        return score;
    }
    public int getTotalScore(){
        int score = 0;
        for (int i = 0; i < 13; i++){
            score += scores[i];
        }
        return score;
    }
    public int getBonusScore(){
        if (getUpperScore() > 63) {
            return 35;
        } else {
            return 0;
        }
    }

    public void printScores(){
        System.out.println("Upper Score - " + getUpperScore());
        System.out.println("Lower Score - " + getLowerScore());
        System.out.println("Total Score - " + getTotalScore());
        System.out.println("Bonus Score - " + getBonusScore());
    }
}
