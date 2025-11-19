package com.evolting.clubms.utils.mappers;

import com.evolting.clubms.entities.Club;
import com.evolting.commonutils.requests.clubs.ClubRequestDto;
import com.evolting.commonutils.responses.clubs.ClubResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class ClubMapper {
    public static Club toEntity(ClubRequestDto dto) {
        return Club.builder()
                .name(dto.getName())
                .foundedYear(dto.getFoundedYear())
                .playerIds(dto.getPlayerIds())
                .build();
    }

    public static ClubResponseDto toResponse(Club entity) {
        return ClubResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .foundedYear(entity.getFoundedYear())
                .avatarUrl(entity.getAvatarUrl())
                .playerIds(
                        entity.getPlayerIds() != null
                                ? new ArrayList<>(entity.getPlayerIds()) // ✅ forces lazy init
                                : Collections.emptyList()
                )
                .build();
    }
}
