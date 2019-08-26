package com.rubanj.casino.rockpaperscissors.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RPSGameStatisticDto {

    private Long userGameId;
    private long gamesCount;
    private long aiWinsCount;
    private long userWinsCont;
    private long tieCont;
    private List<RPSGameDto> games;
}
