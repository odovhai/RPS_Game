package com.rubanj.casino.rockpaperscissors.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rps_games")
public class RPSGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column
    @Enumerated(EnumType.STRING)
    private RPSChoice userChoice;

    @Column
    @Enumerated(EnumType.STRING)
    private RPSChoice aiChoice;

    @Column
    @Enumerated(EnumType.STRING)
    private RPSResult result;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_game_id")
    private UserGame userGame;
}