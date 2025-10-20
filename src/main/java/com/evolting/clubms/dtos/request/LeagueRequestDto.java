package com.evolting.clubms.dtos.request;

import com.evolting.clubms.utils.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeagueRequestDto {

    private String name;

    private Country country;
}