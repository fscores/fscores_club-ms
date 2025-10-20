package com.evolting.clubms.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClubResponseDto {
    private Integer id;
    private String name;
    private Integer foundedYear;
    private Integer leagueId;
    private String leagueName;
    private List<Integer> playerIds;
}
