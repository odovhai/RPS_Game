package com.rubanj.casino.rockpaperscissors.domain.model;

public enum RPSChoice {
    ROCK,
    PAPER,
    SCISSORS;

    private RPSChoice win;

    static {
        ROCK.win = SCISSORS;
        PAPER.win = ROCK;
        SCISSORS.win = PAPER;
    }

    public boolean isWin(RPSChoice choice) {
        return win == choice;
    }

}
