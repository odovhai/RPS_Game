package com.rubanj.casino.rockpaperscissors.repository;

import com.rubanj.casino.rockpaperscissors.domain.model.RPSGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RPSGameRepository extends JpaRepository<RPSGame, Long> {

}
