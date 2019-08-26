package com.rubanj.casino.rockpaperscissors.service.impl;

import com.rubanj.casino.rockpaperscissors.config.RPSGameProperties;
import com.rubanj.casino.rockpaperscissors.domain.dto.RPSGameRequest;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserCredentials;
import com.rubanj.casino.rockpaperscissors.domain.model.*;
import com.rubanj.casino.rockpaperscissors.repository.RPSGameRepository;
import com.rubanj.casino.rockpaperscissors.repository.UserGameRepository;
import com.rubanj.casino.rockpaperscissors.service.GameService;
import com.rubanj.casino.rockpaperscissors.service.RPSChoseMarkovChainProvider;
import com.rubanj.casino.rockpaperscissors.service.UserService;
import com.rubanj.casino.rockpaperscissors.util.CheatingRPSAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private final UserService userService;
    private final UserGameRepository userGameRepository;
    private final RPSGameRepository rpsGameRepository;
    private final RPSChoseMarkovChainProvider chainProvider;
    private final RPSGameProperties rpsGameProperties;

    @Override
    @Transactional
    public UserGame startNewGame(UserCredentials userCredentials) {
        userService.validateUserAccess(userCredentials);
        User user = userService.findByName(userCredentials.getUserName());

        Optional<UserGame> notFinishedGame = userGameRepository.findByUserAndFinishedIsFalse(user);
        if (notFinishedGame.isPresent()) {
            throw new IllegalStateException(String.format("There is already an existing not finished game [%s]", notFinishedGame.get()));
        }

        UserGame userGame = UserGame.builder()
                .user(user)
                .build();
        UserGame result = userGameRepository.save(userGame);
        chainProvider.initNewChain(result.getId());
        return result;
    }

    @Override
    @Transactional
    public UserGame finishLastGame(UserCredentials userCredentials) {
        userService.validateUserAccess(userCredentials);
        User user = userService.findByName(userCredentials.getUserName());
        UserGame userGame = userGameRepository.findByUserAndFinishedIsFalse(user)
                .orElseThrow(() -> new NoSuchElementException("All games are already finished."));
        userGame.setFinished(true);
        UserGame result = userGameRepository.save(userGame);
        chainProvider.evictChain(result.getId());
        return result;
    }

    @Override
    @Transactional
    public RPSGame playRPS(RPSGameRequest rpsGameRequest) {
        userService.validateUserAccess(rpsGameRequest.getUserCredentials());
        UserGame userGame = userGameRepository.findById(rpsGameRequest.getGameId())
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no game with userGameId=[%s]", rpsGameRequest.getGameId())));
        if (userGame.isFinished()) {
            throw new IllegalStateException(String.format("User game with id=[%s] is finished", rpsGameRequest.getGameId()));
        }

        RPSChoice aiChoice = rpsGameProperties.isFairGame()
                ? getMarkovChainChoice(rpsGameRequest, userGame)
                : CheatingRPSAlgorithm.getCheatingChoice(rpsGameRequest.getUserChoice(), rpsGameProperties.getAiWinsPart(), rpsGameProperties.getHumanWinsPart(), rpsGameProperties.getTiePart());

        RPSResult rpsResult = aiChoice == rpsGameRequest.getUserChoice() ? RPSResult.TIE :
                aiChoice.isWin(rpsGameRequest.getUserChoice())
                        ? RPSResult.AI
                        : RPSResult.HUMAN;

        RPSGame rpsGame = RPSGame.builder()
                .userGame(userGame)
                .userChoice(rpsGameRequest.getUserChoice())
                .aiChoice(aiChoice)
                .result(rpsResult)
                .build();
        return rpsGameRepository.save(rpsGame);
    }

    private RPSChoice getMarkovChainChoice(RPSGameRequest rpsGameRequest, UserGame userGame) {
        RPSChoice aiChoice;
        Optional<RPSGame> lastGame = rpsGameRepository.findTopByUserGameOrderByIdDesc(userGame);
        if (lastGame.isPresent()) {
            RPSChoice previousUserChoice = lastGame.get().getUserChoice();
            aiChoice = chainProvider.aiChoice(userGame.getId(), previousUserChoice);
            chainProvider.updateChain(userGame.getId(), previousUserChoice, rpsGameRequest.getUserChoice());
        } else {
            aiChoice = RPSChoice.values()[new Random().nextInt(RPSChoice.values().length)];
        }
        return aiChoice;
    }

    @Override
    @Transactional
    public List<RPSGame> findRpsGames(Long gameId, UserCredentials userCredentials) {
        userService.validateUserAccess(userCredentials);
        User user = userService.findByName(userCredentials.getUserName());
        UserGame userGame = userGameRepository.findByIdAndUser(gameId, user)
                .orElseThrow(() -> new NoSuchElementException(String.format("Could not find game with userGameId=[%s] for user=[%s]", gameId, user)));
        return rpsGameRepository.findByUserGame(userGame);
    }


}
