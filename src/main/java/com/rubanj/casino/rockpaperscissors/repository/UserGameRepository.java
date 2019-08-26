package com.rubanj.casino.rockpaperscissors.repository;

import com.rubanj.casino.rockpaperscissors.domain.model.User;
import com.rubanj.casino.rockpaperscissors.domain.model.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGameRepository extends JpaRepository<UserGame, Long> {

    Optional<UserGame> findByUserAndFinishedIsFalse(User user);
}
