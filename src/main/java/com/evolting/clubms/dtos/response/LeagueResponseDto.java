package com.evolting.clubms.dtos.response;

import com.evolting.clubms.utils.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeagueResponseDto {

    private Integer id;

    private String name;

    private Country country;
}
