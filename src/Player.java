public class Player {
    private String name;
    private int[] scores = new int[13];
    private boolean[] used = new boolean[13];
    private int totalUpper = 0;
    private int totalLower = 0;
    private int bonus = 0;

    public Player(String name){
        this.name = name; // initialize the player with its name
    }

    public void setScores(int categoryIndex, int score){ //adds the score, and make the combination as used after the validated arguments are passed
        scores[categoryIndex] = score;
        used[categoryIndex] = true;
        if (categoryIndex >= 0 && categoryIndex <= 5 ){
            totalUpper += score;
        } else if (categoryIndex >= 6 && categoryIndex <= 12 ){
            totalLower += score;
        }
    }

    public boolean isCategoryAvailable(int categoryIndex) {
        return !used[categoryIndex];
    }

    public void checkBonus() {
        if (totalUpper >= 63) {
            bonus = 35;
        } else {
            bonus = 0;
        }
    }
}
