package com.rubanj.casino.rockpaperscissors.rest;

import com.rubanj.casino.rockpaperscissors.converter.EntityDtoConverter;
import com.rubanj.casino.rockpaperscissors.domain.dto.RPSGameDto;
import com.rubanj.casino.rockpaperscissors.domain.dto.RPSGameRequest;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserCredentials;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserGameDto;
import com.rubanj.casino.rockpaperscissors.domain.model.RPSGame;
import com.rubanj.casino.rockpaperscissors.domain.model.UserGame;
import com.rubanj.casino.rockpaperscissors.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final EntityDtoConverter<UserGame, UserGameDto> userGameConverter;
    private final EntityDtoConverter<RPSGame, RPSGameDto> rpsGameConverter;

    @PostMapping(path = "/start")
    public UserGameDto startNewGame(@RequestBody @Valid UserCredentials userCredentials) {
        return userGameConverter.toDto(gameService.startNewGame(userCredentials));
    }

    @PostMapping(path = "/finish")
    public UserGameDto finishLastGame(@RequestBody @Valid UserCredentials userCredentials) {
        return userGameConverter.toDto(gameService.finishLastGame(userCredentials));
    }

    @PostMapping(path = "/rps")
    public RPSGameDto playRpsGame(@RequestBody @Valid RPSGameRequest request) {
        return rpsGameConverter.toDto(gameService.playRPS(request));
    }
}
