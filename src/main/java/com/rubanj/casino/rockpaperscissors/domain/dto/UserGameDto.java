package com.rubanj.casino.rockpaperscissors.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserGameDto {

    private Long id;
    private boolean finished;
    private Date createdAt;
    private Date updatedAt;

}
