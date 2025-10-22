package com.evolting.clubms.services;

import com.evolting.clubms.dtos.request.ClubRequestDto;
import com.evolting.clubms.dtos.response.ClubResponseDto;

import java.util.List;

public interface ClubService {
    List<ClubResponseDto> getAllClubs(Integer pageNo, Integer pageSize, String sortBy);
    ClubResponseDto getClubById(Integer id);
    ClubResponseDto createClub(ClubRequestDto clubRequestDto);
    ClubResponseDto updateClub(Integer id, ClubRequestDto clubRequestDto);
    void deleteClub(Integer id);
}
