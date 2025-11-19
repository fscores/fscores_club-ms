package com.evolting.clubms.services;

import com.evolting.clubms.dtos.request.ClubSearchDto;
import com.evolting.commonutils.requests.clubs.ClubRequestDto;
import com.evolting.commonutils.requests.players.PlayerRequestDto;
import com.evolting.commonutils.responses.ApiResponseDto;
import com.evolting.commonutils.responses.clubs.ClubResponseDto;
import com.evolting.commonutils.responses.players.PlayerResponseDto;

import java.util.List;

public interface ClubService {
    ApiResponseDto<ClubResponseDto> getClubById(Integer id);
    ApiResponseDto<List<ClubResponseDto>> searchClub(ClubSearchDto clubSearchDto, Integer pageNo, Integer pageSize, String sortBy);
    ApiResponseDto<ClubResponseDto> createClub(ClubRequestDto clubRequestDto);
    ApiResponseDto<ClubResponseDto> updateClub(Integer id, ClubRequestDto clubRequestDto);
    ApiResponseDto<Void> deleteClub(Integer id);
    ApiResponseDto<PlayerResponseDto> createPlayerAndAddToClub(Integer clubId, PlayerRequestDto request);
    ApiResponseDto<PlayerResponseDto> addExistingPlayerToClub(Integer clubId, Integer playerId);
    ApiResponseDto<PlayerResponseDto> changePlayerClub(Integer playerId, Integer newClubId);
}
