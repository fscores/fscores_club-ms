package com.evolting.clubms.services.impl;

import com.evolting.clubms.dtos.response.LeagueResponseDto;
import com.evolting.clubms.repositories.LeagueRepository;
import com.evolting.clubms.services.LeagueService;
import com.evolting.clubms.utils.LeagueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LeagueServiceImpl implements LeagueService {
    private final LeagueRepository leagueRepository;

    @Override
    public List<LeagueResponseDto> getAllLeagues(Integer pageNo, Integer pageSize, String sortBy) {
        List<LeagueResponseDto> leagueResponseDtos = new ArrayList<>();
        try {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            leagueResponseDtos = leagueRepository.findAll(paging)
                    .stream()
                    .map(LeagueMapper::toLeagueResponseDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error retrieving all leagues", e);
        }
        return leagueResponseDtos;
    }

    @Override
    public LeagueResponseDto getLeagueById(Integer id) {
        return null;
    }

    @Override
    public LeagueResponseDto createLeague(LeagueResponseDto leagueResponseDto) {
        return null;
    }

    @Override
    public LeagueResponseDto updateLeague(Integer id, LeagueResponseDto leagueResponseDto) {
        return null;
    }

    @Override
    public void deleteLeagueById(Integer id) {

    }
}
