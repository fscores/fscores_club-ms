package com.evolting.clubms.services.impl;

import com.evolting.clubms.dtos.request.ClubRequestDto;
import com.evolting.clubms.dtos.response.ClubResponseDto;
import com.evolting.clubms.entities.Club;
import com.evolting.clubms.repositories.ClubRepository;
import com.evolting.clubms.services.ClubService;
import com.evolting.clubms.utils.mappers.ClubMapper;
import jakarta.persistence.EntityNotFoundException;
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
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;

    @Override
    public List<ClubResponseDto> getAllClubs(Integer pageNo, Integer pageSize, String sortBy) {
        List<ClubResponseDto> clubResponseDtos = new ArrayList<>();
        try {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            clubResponseDtos = clubRepository.findAll(paging)
                    .stream()
                    .map(ClubMapper::toResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error retrieving all clubs", e);
        }
        return clubResponseDtos;
    }

    @Override
    public ClubResponseDto getClubById(Integer id) {
        ClubResponseDto clubResponseDto = null;
        try {
            Club club = clubRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Club not found with ID: " + id));
            clubResponseDto = ClubMapper.toResponse(club);
        } catch (Exception e) {
            log.error("Error retrieving club with ID {}", id, e);
        }
        return clubResponseDto;
    }

    @Override
    public ClubResponseDto createClub(ClubRequestDto clubRequestDto) {
        try {
            log.info("Creating club with data: {}", ClubMapper.toEntity(clubRequestDto).toString());
            Club club = clubRepository.save(ClubMapper.toEntity(clubRequestDto));
            return ClubMapper.toResponse(club);
        } catch (Exception e) {
            log.error("Error creating club", e);
        }
        return null;
    }

    @Override
    public ClubResponseDto updateClub(Integer id, ClubRequestDto clubRequestDto) {
        try {
            Club club = ClubMapper.toEntity(clubRequestDto);
            club.setId(id);
            club = clubRepository.save(club);
            return ClubMapper.toResponse(club);
        } catch (Exception e) {
            log.error("Error updating club", e);
        }
        return null;
    }

    @Override
    public void deleteClub(Integer id) {
        try {
            clubRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting club with ID {}", id, e);
        }
    }
}
