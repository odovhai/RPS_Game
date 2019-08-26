package com.rubanj.casino.rockpaperscissors.domain.dto;

import com.rubanj.casino.rockpaperscissors.domain.model.RPSChoice;
import com.rubanj.casino.rockpaperscissors.domain.model.RPSResult;
import lombok.Data;

import java.util.Date;

@Data
public class RPSGameDto {

    private Long id;
    private boolean finished;
    private Date createdAt;
    private RPSChoice userChoice;
    private RPSChoice aiChoice;
    private RPSResult result;
}
