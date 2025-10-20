package com.evolting.clubms.utils;

import com.evolting.clubms.dtos.request.LeagueRequestDto;
import com.evolting.clubms.dtos.response.LeagueResponseDto;
import com.evolting.clubms.entities.League;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * Static utility class to map between League entities and DTOs.
 */
public class LeagueMapper {

    /**
     * Converts a LeagueRequestDto (for creation/update) into a League entity.
     *
     * @param dto The DTO object received from the API.
     * @return The Entity object ready for database persistence.
     */
    public static League toEntity(LeagueRequestDto dto) {
        if (dto == null) {
            return null;
        }

        League entity = new League();
        // We don't set ID, as it's for creation
        entity.setName(dto.getName());
        entity.setCountry(dto.getCountry());
        // 'clubs' list defaults to an empty ArrayList in the entity definition

        return entity;
    }

    /**
     * Converts a League entity into a LeagueResponseDto (the simple view).
     *
     * @param league The strongly-typed JPA entity.
     * @return The DTO object for API transport.
     */
    public static LeagueResponseDto toLeagueResponseDto(League league) {
        if (league == null) {
            return null;
        }

        LeagueResponseDto dto = new LeagueResponseDto();
        dto.setId(league.getId());
        dto.setName(league.getName());
        dto.setCountry(league.getCountry());

        return dto;
    }
}
