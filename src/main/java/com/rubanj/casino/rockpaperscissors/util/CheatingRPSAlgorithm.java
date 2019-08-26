package com.rubanj.casino.rockpaperscissors.util;

import com.rubanj.casino.rockpaperscissors.domain.model.RPSChoice;
import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class CheatingRPSAlgorithm {

    public static RPSChoice getCheatingChoice(RPSChoice userChoice, int aiWinsPart, int humanWinsPart, int tiePart) {
        int totalPart = aiWinsPart + humanWinsPart + tiePart;
        int random = ThreadLocalRandom.current().nextInt(1, totalPart);

        if (1 <= random && random <= aiWinsPart) {
            return userChoice.getLose();
        }

        if (aiWinsPart + 1 <= random && random <= aiWinsPart + humanWinsPart) {
            return userChoice.getWin();
        }

        if (aiWinsPart + humanWinsPart + 1 <= random && random <= totalPart) {
            return userChoice;
        }

        return RPSChoice.values()[new Random().nextInt(RPSChoice.values().length)];
    }
}
