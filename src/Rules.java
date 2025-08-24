package Yahtzee.src;

public class Rules {
    public int chance(int[] dices){
        int sum = 0;
        for (int i = 0; i < 5;i++){
            sum = sum + dices[i];
        }
        return sum;
    }

    public int yahtzee(int[] dices){
        int sum = 50;
        int temp = dices[0];
        for (int i = 0; i < 5; i++){
            if(!(temp == dices[i])){
                sum = 0;
                break;
            }
        }
        return sum;
    }

    public int largeStraight(int[] dices) {
        int array[] = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            array[dices[i] - 1]++ ;
        }
        if((array[0] == 1 && array[1] == 1 && array[2] == 1 && array[3] == 1 && array[4] == 1) ||
            (array[1] == 1 && array[2] == 1 && array[3] == 1 && array[4] == 1 && array[5] == 1)) {
            return 40;
        } else {
            return 0;
        }
    }

    public int smallStraight(int[] dices) {
        int array[] = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            array[dices[i] - 1]++ ;
        }
        if((array[0] >= 1 && array[1] >= 1 && array[2] >= 1 && array[3] >= 1) ||
           (array[1] >= 1 && array[2] >= 1 && array[3] >= 1 && array[4] >= 1) ||
           (array[2] >= 1 && array[3] >= 1 && array[4] >= 1 && array[5] >= 1)) {
            return 30;
        } else {
            return 0;
        }
    }

    public int fullHouse(int[] dices){
        int sum = 0;
        int array1[] = {0, 0, 0, 0, 0, 0};
        int kindOfThreeCheck = 0;
        int kindOfTwoCheck = 0;
        for (int i = 0; i < 5; i++) {
            array1[dices[i] - 1]++ ;
        }
        for (int j = 0; j < 6; j++) {
            if (array1[j] == 3){
                kindOfThreeCheck = 1;
            }
        }
        for (int k = 0; k < 6; k++){
            if (array1[k] == 2){
                kindOfTwoCheck = 1;
            } 
        }
        if((kindOfThreeCheck == 1) && (kindOfTwoCheck == 1)) {
            sum = 25;
        } else {
            sum = 0;
        }
        return sum;
    }

    public int fourOfaKind(int[] dices){
        int sum = 0;
        int array1[] = {0, 0, 0, 0, 0, 0};
        for (int ii = 0; ii < 5; ii++) {
            array1[dices[ii] - 1]++ ;
        }
        for (int j = 0; j < 5; j++) {
            if (array1[j] >= 4){
                for (int i = 0; i < 5; i++){
                    sum += dices[i];
                }
                return sum;
            }
        }
        return 0;
    }

    public int threeOfaKind(int[] dices){
        int sum = 0;
        int array1[] = {0, 0, 0, 0, 0, 0};
        for (int ii = 0; ii < 5; ii++) {
            array1[dices[ii] - 1]++ ;
        }
        for (int j = 0; j < 5; j++) {
            if (array1[j] >= 3){
                for (int i = 0; i < 5; i++){
                    sum += dices[i];
                }
                return sum;
            }
        }
        return 0;
    }

    public int ones(int[] dices){
        int score = 0;
        for (int i = 0; i < 5; i++) {
            if(dices[i] == 1) {
                score += 1;
            }
        }
        return score;
    }

    public int twos(int[] dices){
        int score = 0;
        for (int i = 0; i < 5; i++) {
            if(dices[i] == 2) {
                score += 2;
            }
        }
        return score;
    }

    public int threes(int[] dices){
        int score = 0;
        for (int i = 0; i < 5; i++) {
            if(dices[i] == 3) {
                score += 3;
            }
        }
        return score;
    }

    public int fours(int[] dices){
        int score = 0;
        for (int i = 0; i < 5; i++) {
            if(dices[i] == 4) {
                score += 4;
            }
        }
        return score;
    }

    public int fives(int[] dices){
        int score = 0;
        for (int i = 0; i < 5; i++) {
            if(dices[i] == 5) {
                score += 5;
            }
        }
        return score;
    }

    public int sixes(int[] dices){
        int score = 0;
        for (int i = 0; i < 5; i++) {
            if(dices[i] == 6) {
                score += 6;
            }
        }
        return score;
    }

    public int getScore(int combinationIndex, int[] dices) {
        switch(combinationIndex) {
            case 0:
                return ones(dices);
            case 1:
                return twos(dices);
            case 2: 
                return threes(dices);
            case 3:
                return fours(dices);
            case 4:
                return fives(dices);
            case 5: 
                return sixes(dices);
            case 6:
                return threeOfaKind(dices);
            case 7:
                return fourOfaKind(dices);    
            case 8:
                return fullHouse(dices);
            case 9:
                return smallStraight(dices);
            case 10:
                return largeStraight(dices);
            case 11:
                return chance(dices);
            case 12: 
                return yahtzee(dices);
            default:
                return 0;
        }
    }
}
