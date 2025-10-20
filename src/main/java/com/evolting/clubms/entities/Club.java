package com.evolting.clubms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer foundedYear;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY is good for performance
    @JoinColumn(name = "league_id")
    @JsonIgnore // Prevents infinite loops when serializing to JSON
    private League league;

    @ElementCollection
    @CollectionTable(
            name = "club_player_ids",
            joinColumns = @JoinColumn(name = "club_id")
    )
    @Column(name = "player_id")
    private List<Integer> playerIds = new ArrayList<>();

    public String getLeagueName() {
        return (league != null) ? league.getName() : null;
    }
}
