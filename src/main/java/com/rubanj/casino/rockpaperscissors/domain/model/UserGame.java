package com.rubanj.casino.rockpaperscissors.domain.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_games")
public class UserGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean finished;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @LastModifiedDate
    private Date updatedAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "userGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RPSGame> rpsGames;
}