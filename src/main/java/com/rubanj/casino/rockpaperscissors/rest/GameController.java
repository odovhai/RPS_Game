package com.rubanj.casino.rockpaperscissors.rest;

import com.rubanj.casino.rockpaperscissors.converter.EntityDtoConverter;
import com.rubanj.casino.rockpaperscissors.domain.dto.*;
import com.rubanj.casino.rockpaperscissors.domain.model.RPSGame;
import com.rubanj.casino.rockpaperscissors.domain.model.RPSResult;
import com.rubanj.casino.rockpaperscissors.domain.model.UserGame;
import com.rubanj.casino.rockpaperscissors.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final EntityDtoConverter<UserGame, UserGameDto> userGameConverter;
    private final EntityDtoConverter<RPSGame, RPSGameDto> rpsGameConverter;

    @GetMapping
    public List<UserGameDto> getAll() {
        List<UserGame> result = gameService.findAll();
        return userGameConverter.toDtoList(result);
    }

    @PostMapping(path = "/start")
    public UserGameDto startNewGame(@RequestBody @Valid UserCredentials userCredentials) {
        return userGameConverter.toDto(gameService.startNewGame(userCredentials));
    }

    @PostMapping(path = "/finish")
    public UserGameDto finishLastGame(@RequestBody @Valid UserCredentials userCredentials) {
        return userGameConverter.toDto(gameService.finishLastGame(userCredentials));

    }

    @PostMapping(path = "/{gameId}")
    public RPSGameStatisticDto getStatistics(@PathVariable Long gameId, @RequestBody @Valid UserCredentials userCredentials) {

        List<RPSGame> rpsGames = gameService.findRpsGames(gameId, userCredentials);

        return RPSGameStatisticDto.builder()
                .userGameId(gameId)
                .gamesCount(rpsGames.size())
                .aiWinsCount(rpsGames.stream().filter(g -> g.getResult() == RPSResult.AI).count())
                .userWinsCont(rpsGames.stream().filter(g -> g.getResult() == RPSResult.HUMAN).count())
                .tieCont(rpsGames.stream().filter(g -> g.getResult() == RPSResult.TIE).count())
                .games(rpsGameConverter.toDtoList(rpsGames))
                .build();
    }

    @PostMapping(path = "/rps")
    public RPSGameDto playRpsGame(@RequestBody @Valid RPSGameRequest request) {
        return rpsGameConverter.toDto(gameService.playRPS(request));
    }
}
