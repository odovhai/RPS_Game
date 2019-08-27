package com.rubanj.casino.rockpaperscissors;

import com.rubanj.casino.rockpaperscissors.domain.dto.RPSGameRequest;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserCredentials;
import com.rubanj.casino.rockpaperscissors.domain.model.*;
import com.rubanj.casino.rockpaperscissors.repository.RPSGameRepository;
import com.rubanj.casino.rockpaperscissors.repository.UserGameRepository;
import com.rubanj.casino.rockpaperscissors.service.GameService;
import com.rubanj.casino.rockpaperscissors.service.UserService;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicesIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserGameRepository userGameRepository;
    @Autowired
    private RPSGameRepository rpsGameRepository;

    private static final int PLAYS_COUNT = 42;

    @Test
    public void servicesIntegrationPositiveTest() {

        User user = new User();
        user.setName("Usr1");
        user.setPassword("pass1");
        userService.create(user);

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName("Usr1");
        userCredentials.setPassword("pass1");
        UserGame userGame = gameService.startNewGame(userCredentials);

        RPSGameRequest rpsGameRequest = new RPSGameRequest();
        rpsGameRequest.setGameId(userGame.getId());
        rpsGameRequest.setUserCredentials(userCredentials);

        for (int i = 0; i < PLAYS_COUNT; i++) {
            rpsGameRequest.setUserChoice(RPSChoice.values()[new Random().nextInt(RPSChoice.values().length)]);
            gameService.playRPS(rpsGameRequest);
        }

        gameService.finishLastGame(userCredentials);

        Optional<UserGame> actualUserGame = userGameRepository.findById(userGame.getId());

        Assert.assertThat(actualUserGame.isPresent(), Is.is(true));
        Assert.assertThat(actualUserGame.get().isFinished(), Is.is(true));

        List<RPSGame> actualRpsGames = rpsGameRepository.findByUserGame(actualUserGame.get());

        Assert.assertThat(actualRpsGames.size(), Is.is(PLAYS_COUNT));

        for (RPSGame actualRpsGame : actualRpsGames) {
            RPSResult expectedResult;
            if (actualRpsGame.getAiChoice() == actualRpsGame.getUserChoice()) {
                expectedResult = RPSResult.TIE;
            } else if (actualRpsGame.getAiChoice().isWin(actualRpsGame.getUserChoice())) {
                expectedResult = RPSResult.AI;
            } else {
                expectedResult = RPSResult.HUMAN;
            }
            Assert.assertThat(actualRpsGame.getResult(), Is.is(expectedResult));
        }
    }

    @Test
    public void serviceIntegrationNegativeTest() {
        User user = new User();
        user.setName("Usr2");
        user.setPassword("pass2");
        userService.create(user);

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName("Usr2");
        userCredentials.setPassword("pass2");
        UserGame userGame = gameService.startNewGame(userCredentials);

        IllegalStateException expectedNewGaeException = null;
        try {
            gameService.startNewGame(userCredentials);
        } catch (IllegalStateException ex) {
            expectedNewGaeException = ex;
        }
        Assert.assertThat(expectedNewGaeException, IsNull.notNullValue());

        RPSGameRequest rpsGameRequest = new RPSGameRequest();
        rpsGameRequest.setGameId(42222L);
        userCredentials.setPassword("wrong");
        rpsGameRequest.setUserCredentials(userCredentials);
        rpsGameRequest.setUserChoice(RPSChoice.ROCK);
        BadCredentialsException expectedBadCredentialsException = null;
        try {
            gameService.playRPS(rpsGameRequest);
        } catch (BadCredentialsException ex) {
            expectedBadCredentialsException = ex;
        }
        Assert.assertThat(expectedBadCredentialsException, IsNull.notNullValue());

        userCredentials.setPassword("pass2");
        NoSuchElementException expectedNoSuchGameException = null;
        try {
            gameService.playRPS(rpsGameRequest);
        } catch (NoSuchElementException ex) {
            expectedNoSuchGameException = ex;
        }
        Assert.assertThat(expectedNoSuchGameException, IsNull.notNullValue());

        rpsGameRequest.setGameId(userGame.getId());
        gameService.playRPS(rpsGameRequest);

        userCredentials.setPassword("wrong");

        BadCredentialsException expectedBadCredentialsException2 = null;
        try {
            gameService.finishLastGame(userCredentials);
        } catch (BadCredentialsException ex) {
            expectedBadCredentialsException2 = ex;
        }
        Assert.assertThat(expectedBadCredentialsException2, IsNull.notNullValue());

        userCredentials.setPassword("pass2");
        gameService.finishLastGame(userCredentials);

        IllegalStateException expectedFinishedGameException = null;
        try {
            gameService.playRPS(rpsGameRequest);
        } catch (IllegalStateException ex) {
            expectedFinishedGameException = ex;
        }
        Assert.assertThat(expectedFinishedGameException, IsNull.notNullValue());
    }
}


