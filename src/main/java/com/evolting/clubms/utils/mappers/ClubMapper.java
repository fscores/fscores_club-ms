package com.evolting.clubms.utils.mappers;

import com.evolting.clubms.dtos.request.ClubRequestDto;
import com.evolting.clubms.dtos.response.ClubResponseDto;
import com.evolting.clubms.entities.Club;
import org.springframework.stereotype.Component;

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
                .playerIds(entity.getPlayerIds())
                .build();
    }
}
