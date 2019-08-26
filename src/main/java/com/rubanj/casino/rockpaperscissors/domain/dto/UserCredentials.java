package com.rubanj.casino.rockpaperscissors.domain.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.validation.constraints.NotBlank;

@Data
public class UserCredentials {
    @NotBlank
    private String userName;
    @NotBlank
    @ToStringExclude
    private String password;
}
