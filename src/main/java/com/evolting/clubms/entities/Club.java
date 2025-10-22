package com.evolting.clubms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer foundedYear;
    private String avatarUrl;

    @ElementCollection
    @CollectionTable(
            name = "club_player_ids",
            joinColumns = @JoinColumn(name = "club_id")
    )
    @Column(name = "player_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> playerIds = new ArrayList<>();
}
