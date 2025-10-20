package com.evolting.clubms.services;

import com.evolting.clubms.dtos.response.LeagueResponseDto;

import java.util.List;

public interface LeagueService {
    List<LeagueResponseDto> getAllLeagues(Integer pageNo, Integer pageSize, String sortBy);
    LeagueResponseDto getLeagueById(Integer id);
    LeagueResponseDto createLeague(LeagueResponseDto leagueResponseDto);
    LeagueResponseDto updateLeague(Integer id, LeagueResponseDto leagueResponseDto);
    void deleteLeagueById(Integer id);
}
