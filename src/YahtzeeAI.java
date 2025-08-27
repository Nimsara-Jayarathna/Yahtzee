package Yahtzee.src;

import java.util.ArrayList;
import java.util.List;

public class YahtzeeAI {

    private Rules rules = new Rules();

    /**
     * This is the main method that controls the AI's entire turn.
     * It performs an initial analysis, re-rolls up to two times to improve the hand,
     * and then makes a final decision on which combination to score.
     *
     * @param dices  The AI's Dices object for this turn.
     * @param player The AI's Player object, which tracks used combinations and scores.
     * @param roundNo The current round number of the game (0-12).
     */
    public void takeTurn(Dices dices, Player player, int roundNo) {
        int targetCombinationIndex;

        // Loop for up to two re-rolls
        for (int rollNum = 0; rollNum < 2; rollNum++) {
            // Step 1: Analyze the current hand to decide what to aim for.
            targetCombinationIndex = analyzeAndChooseTarget(dices.getDices(), player, roundNo);

            // Step 2: Determine which dice to re-roll based on the target.
            List<Integer> diceToReRoll = determineDiceToReRoll(dices.getDices(), targetCombinationIndex);

            // Step 3: If there are dice to re-roll, roll them. Otherwise, the hand is perfect for the target.
            if (!diceToReRoll.isEmpty()) {
                System.out.println(player.getPlayerName() + " is re-rolling for: " + Player.COMBINATIONS[targetCombinationIndex]);
                for (int index : diceToReRoll) {
                    dices.rollDice(index); // Re-roll specific dice
                }
                System.out.println("Roll " + (rollNum + 2) + ":");
                dices.printDices();
            } else {
                // If we don't need to re-roll, break the loop early.
                System.out.println(player.getPlayerName() + " is happy with the hand.");
                break;
            }
        }

        // After all rolls, make the final scoring decision.
        scoreBestOption(dices.getDices(), player);
    }

    /**
     * Analyzes the dice and available combinations to decide the best combination to AIM FOR.
     * This helps guide the re-roll strategy.
     */
    private int analyzeAndChooseTarget(int[] dices, Player player, int roundNo) {
        int bestScore = -1;
        int targetIndex = -1;

        // Prioritize high-value combinations if they are already achieved.
        if (!player.getCombinationUsed(12) && rules.yahtzee(dices) > 0) return 12; // Yahtzee
        if (!player.getCombinationUsed(10) && rules.largeStraight(dices) > 0) return 10; // Large Straight
        if (!player.getCombinationUsed(8) && rules.fullHouse(dices) > 0) return 8; // Full House

        // Otherwise, find the highest possible score among available combinations.
        for (int i = 0; i < 13; i++) {
            if (!player.getCombinationUsed(i)) {
                // Skip 'Chance' until the later rounds unless it's the only option
                if (i == 11 && roundNo < 9) {
                    continue;
                }
                int currentScore = rules.getScore(i, dices);
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    targetIndex = i;
                }
            }
        }

        // If no scoring option is found (all scores are 0), find the first available slot to put a zero.
        if (targetIndex == -1) {
            for (int i = 0; i < 13; i++) {
                if (!player.getCombinationUsed(i)) {
                    return i;
                }
            }
        }

        return targetIndex;
    }

    /**
     * Based on a target combination, this method decides which dice should be re-rolled.
     * Returns a list of dice indices (0-4) to re-roll.
     */
    private List<Integer> determineDiceToReRoll(int[] dices, int targetCombinationIndex) {
        List<Integer> diceToReRoll = new ArrayList<>();
        int[] counts = new int[6];
        for (int die : dices) {
            counts[die - 1]++;
        }

        // Strategy for Upper Section (Ones to Sixes)
        if (targetCombinationIndex >= 0 && targetCombinationIndex <= 5) {
            int targetNumber = targetCombinationIndex + 1;
            for (int i = 0; i < 5; i++) {
                if (dices[i] != targetNumber) {
                    diceToReRoll.add(i);
                }
            }
        }
        // Strategy for Three or Four of a Kind or Yahtzee
        else if (targetCombinationIndex == 6 || targetCombinationIndex == 7 || targetCombinationIndex == 12) {
            int majorityNumber = findMostFrequentNumber(counts);
            for (int i = 0; i < 5; i++) {
                if (dices[i] != majorityNumber) {
                    diceToReRoll.add(i);
                }
            }
        }
        // Basic strategy for straights: keep unique numbers and re-roll duplicates.
        // A more advanced AI would identify the longest sequence.
        else if (targetCombinationIndex == 9 || targetCombinationIndex == 10) {
            for (int i = 0; i < 6; i++) {
                if (counts[i] > 1) {
                    // Find one of the duplicates to re-roll
                    for(int j = 0; j < 5; j++) {
                        if (dices[j] == (i + 1)) {
                            diceToReRoll.add(j);
                            break; // only add one of the duplicates
                        }
                    }
                }
            }
        }

        return diceToReRoll;
    }

    /**
     * After all rolls are done, this method evaluates the final hand and scores the best possible combination.
     */
    private void scoreBestOption(int[] dices, Player player) {
        int bestScore = -1;
        int finalIndex = -1;

        // Find the highest score from all available combinations with the final dice.
        for (int i = 0; i < 13; i++) {
            if (!player.getCombinationUsed(i)) {
                int currentScore = rules.getScore(i, dices);
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    finalIndex = i;
                }
            }
        }

        // If no combination yields a score > -1 (e.g., all available slots score 0),
        // we must find an available slot to place a zero.
        if (finalIndex == -1) {
             for (int i = 0; i < 13; i++) {
                 // Prioritize using a low-value upper section slot for a zero.
                if (!player.getCombinationUsed(i) && i < 6) {
                    finalIndex = i;
                    bestScore = 0;
                    break;
                }
            }
             // If upper section is full, use any other available slot.
            if (finalIndex == -1) {
                for (int i = 6; i < 13; i++) {
                    if (!player.getCombinationUsed(i)) {
                        finalIndex = i;
                        bestScore = 0;
                        break;
                    }
                }
            }
        }

        System.out.println(player.getPlayerName() + " has chosen to score " + bestScore + " points for " + Player.COMBINATIONS[finalIndex] + ".");
        player.setScore(finalIndex, bestScore);
    }

    /**
     * Helper method to find the most frequent dice value.
     */
    private int findMostFrequentNumber(int[] counts) {
        int majorityNumber = 1;
        int maxCount = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > maxCount) {
                maxCount = counts[i];
                majorityNumber = i + 1;
            }
        }
        return majorityNumber;
    }
}