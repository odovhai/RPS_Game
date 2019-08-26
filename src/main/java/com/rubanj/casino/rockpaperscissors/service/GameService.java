package com.rubanj.casino.rockpaperscissors.service;

import com.rubanj.casino.rockpaperscissors.domain.dto.RPSGameRequest;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserCredentials;
import com.rubanj.casino.rockpaperscissors.domain.model.RPSGame;
import com.rubanj.casino.rockpaperscissors.domain.model.UserGame;

import java.util.List;

public interface GameService {

    UserGame startNewGame(UserCredentials userCredentials);

    UserGame finishLastGame(UserCredentials userCredentials);

    RPSGame playRPS(RPSGameRequest rpsGameRequest);

    List<RPSGame> findRpsGames(Long gameId, UserCredentials userCredentials);

}
