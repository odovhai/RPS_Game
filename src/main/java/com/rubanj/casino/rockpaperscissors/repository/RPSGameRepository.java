package com.rubanj.casino.rockpaperscissors.repository;

import com.rubanj.casino.rockpaperscissors.domain.model.RPSGame;
import com.rubanj.casino.rockpaperscissors.domain.model.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RPSGameRepository extends JpaRepository<RPSGame, Long> {

    Optional<RPSGame> findTopByUserGameOrderByIdDesc(UserGame userGame);
    List<RPSGame> findByUserGame(UserGame userGame);

}
