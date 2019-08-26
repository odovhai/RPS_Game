package com.rubanj.casino.rockpaperscissors.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app.rps-game")
public class RPSGameProperties {
    private boolean fairGame;
    private int aiWinsPart;
    private int humanWinsPart;
    private int tiePart;
}
