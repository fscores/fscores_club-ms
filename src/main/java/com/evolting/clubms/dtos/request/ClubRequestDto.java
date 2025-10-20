package com.evolting.clubms.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClubRequestDto {
    private String name;
    private Integer foundedYear;
    private Integer leagueId;
    private List<Integer> playerIds;
}
