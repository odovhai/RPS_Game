package com.rubanj.casino.rockpaperscissors.domain.dto;

import com.rubanj.casino.rockpaperscissors.domain.model.RPSChoice;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class RPSGameRequest {
    @NotNull
    @Positive
    private Long gameId;
    @NotNull
    private RPSChoice userChoice;
    @NotNull
    @Valid
    private UserCredentials userCredentials;
}
