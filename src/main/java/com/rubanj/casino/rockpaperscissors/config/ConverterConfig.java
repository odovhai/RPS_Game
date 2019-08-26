package com.rubanj.casino.rockpaperscissors.config;

import com.rubanj.casino.rockpaperscissors.converter.EntityDtoConverter;
import com.rubanj.casino.rockpaperscissors.converter.impl.DefaultDozerEntityDtoConverter;
import com.rubanj.casino.rockpaperscissors.domain.dto.RPSGameDto;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserDto;
import com.rubanj.casino.rockpaperscissors.domain.dto.UserGameDto;
import com.rubanj.casino.rockpaperscissors.domain.model.RPSGame;
import com.rubanj.casino.rockpaperscissors.domain.model.User;
import com.rubanj.casino.rockpaperscissors.domain.model.UserGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

    @Bean
    public EntityDtoConverter<User, UserDto> mailboxConverter() {
        return new DefaultDozerEntityDtoConverter<>(User.class, UserDto.class);
    }

    @Bean
    public EntityDtoConverter<UserGame, UserGameDto> userGameConverter() {
        return new DefaultDozerEntityDtoConverter<>(UserGame.class, UserGameDto.class);
    }
    @Bean
    public EntityDtoConverter<RPSGame, RPSGameDto> RPSGameConverter() {
        return new DefaultDozerEntityDtoConverter<>(RPSGame.class, RPSGameDto.class);
    }

}
