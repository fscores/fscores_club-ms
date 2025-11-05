package com.evolting.clubms.services;

import com.evolting.clubms.dtos.request.ClubRequestDto;
import com.evolting.clubms.dtos.request.ClubSearchDto;
import com.evolting.clubms.dtos.response.ApiResponseDto;
import com.evolting.clubms.dtos.response.ClubResponseDto;

import java.util.List;

public interface ClubService {
    ApiResponseDto<ClubResponseDto> getClubById(Integer id);
    ApiResponseDto<List<ClubResponseDto>> searchClub(ClubSearchDto clubSearchDto, Integer pageNo, Integer pageSize, String sortBy);
    ApiResponseDto<ClubResponseDto> createClub(ClubRequestDto clubRequestDto);
    ApiResponseDto<ClubResponseDto> updateClub(Integer id, ClubRequestDto clubRequestDto);
    ApiResponseDto<Void> deleteClub(Integer id);
}
