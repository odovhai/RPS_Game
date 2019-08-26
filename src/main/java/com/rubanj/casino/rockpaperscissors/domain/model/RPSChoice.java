package com.rubanj.casino.rockpaperscissors.domain.model;

public enum RPSChoice {
    ROCK,
    PAPER,
    SCISSORS;

    private RPSChoice win;
    private RPSChoice lose;

    static {
        ROCK.win = SCISSORS;
        ROCK.lose = PAPER;
        PAPER.win = ROCK;
        PAPER.lose = SCISSORS;
        SCISSORS.win = PAPER;
        SCISSORS.lose = ROCK;
    }

    public boolean isWin(RPSChoice choice) {
        return win == choice;
    }

    public RPSChoice getWin() {
        return win;
    }

    public RPSChoice getLose() {
        return lose;
    }
}
