package com.rubanj.casino.rockpaperscissors.util;

import com.rubanj.casino.rockpaperscissors.domain.model.RPSChoice;

public final class MarkovChainRPS {

    private volatile int[][] chain;

    public static MarkovChainRPS create() {
        return new MarkovChainRPS();
    }

    private MarkovChainRPS() {
        int length = RPSChoice.values().length;
        chain = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                chain[i][j] = 0;
            }
        }
    }

    public RPSChoice aiChoice(RPSChoice previousUserChoice) {
        int predictedIndex = 0;
        for (int i = 0; i < RPSChoice.values().length; i++) {
            int previousIndex = previousUserChoice.ordinal();
            if (chain[previousIndex][i] > chain[previousIndex][predictedIndex]) {
                predictedIndex = i;
            }
        }
        RPSChoice predictedUserChoice = RPSChoice.values()[predictedIndex];
        return predictedUserChoice.getLose();
    }

    public void update(RPSChoice previousChoice, RPSChoice currentChoice) {
        synchronized (this) {
            chain[previousChoice.ordinal()][currentChoice.ordinal()]++;
        }
    }

}