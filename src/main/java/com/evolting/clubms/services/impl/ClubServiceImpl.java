package com.evolting.clubms.services.impl;

import com.evolting.clubms.dtos.request.ClubRequestDto;
import com.evolting.clubms.dtos.request.ClubSearchDto;
import com.evolting.clubms.dtos.response.ApiResponseDto;
import com.evolting.clubms.dtos.response.ClubResponseDto;
import com.evolting.clubms.entities.Club;
import com.evolting.clubms.helpers.ClubSpecifications;
import com.evolting.clubms.helpers.Metadata;
import com.evolting.clubms.repositories.ClubRepository;
import com.evolting.clubms.services.ClubService;
import com.evolting.clubms.utils.constants.ResponseMessages;
import com.evolting.clubms.utils.mappers.ClubMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;

    @Cacheable(value = "clubs", key = "#id")
    @Override
    public ApiResponseDto<ClubResponseDto> getClubById(Integer id) {
        try {
            Club club = clubRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Club not found with ID: " + id));

            ClubResponseDto clubResponseDto = ClubMapper.toResponse(club);

            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(true)
                    .message(ResponseMessages.SUCCESS)
                    .data(clubResponseDto)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();

        } catch (EntityNotFoundException e) {
            log.warn("Club not found: {}", e.getMessage());
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();

        } catch (Exception e) {
            log.error("Unexpected error retrieving club with ID {}", id, e);
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();
        }
    }

    @Override
    public ApiResponseDto<List<ClubResponseDto>> searchClub(
            ClubSearchDto clubSearchDto, Integer pageNo, Integer pageSize, String sortBy) {

        try {
            Specification<Club> spec = ClubSpecifications.buildFromDto(clubSearchDto);
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            Page<Club> clubPage = clubRepository.findAll(spec, paging);

            List<ClubResponseDto> clubResponseDtos = clubPage.getContent()
                    .stream()
                    .map(ClubMapper::toResponse)
                    .toList();

            Metadata metadata = Metadata.builder()
                    .totalPages(clubPage.getTotalPages())
                    .totalElements(clubPage.getTotalElements())
                    .currentPage(clubPage.getNumber())
                    .pageSize(clubPage.getSize())
                    .timestamp(LocalDateTime.now().toString())
                    .build();

            return ApiResponseDto.<List<ClubResponseDto>>builder()
                    .success(true)
                    .message(ResponseMessages.SUCCESS)
                    .data(clubResponseDtos)
                    .meta(metadata)
                    .build();

        } catch (Exception e) {
            log.error("Error retrieving clubs with filter: {}", clubSearchDto, e);
            return ApiResponseDto.<List<ClubResponseDto>>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(Collections.emptyList())
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();
        }
    }

    @Override
    public ApiResponseDto<ClubResponseDto> createClub(ClubRequestDto clubRequestDto) {
        try {
            Club club = clubRepository.save(ClubMapper.toEntity(clubRequestDto));
            ClubResponseDto response = ClubMapper.toResponse(club);

            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(true)
                    .message(ResponseMessages.CREATED)
                    .data(response)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();

        } catch (Exception e) {
            log.error("Error creating club: {}", clubRequestDto, e);
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();
        }
    }

    @Override
    public ApiResponseDto<ClubResponseDto> updateClub(Integer id, ClubRequestDto clubRequestDto) {
        try {
            if (!clubRepository.existsById(id)) {
                return ApiResponseDto.<ClubResponseDto>builder()
                        .success(false)
                        .message(ResponseMessages.NOT_FOUND + " Club with ID: " + id)
                        .data(null)
                        .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                        .build();
            }

            Club club = ClubMapper.toEntity(clubRequestDto);
            club.setId(id);
            club = clubRepository.save(club);

            ClubResponseDto response = ClubMapper.toResponse(club);

            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(true)
                    .message(ResponseMessages.UPDATED)
                    .data(response)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();

        } catch (Exception e) {
            log.error("Error updating club with ID {}", id, e);
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();
        }
    }

    @Override
    public ApiResponseDto<Void> deleteClub(Integer id) {
        try {
            if (!clubRepository.existsById(id)) {
                return ApiResponseDto.<Void>builder()
                        .success(false)
                        .message("Club not found with ID: " + id)
                        .data(null)
                        .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                        .build();
            }

            clubRepository.deleteById(id);

            return ApiResponseDto.<Void>builder()
                    .success(true)
                    .message(ResponseMessages.DELETED)
                    .data(null)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();

        } catch (Exception e) {
            log.error("Error deleting club with ID {}", id, e);
            return ApiResponseDto.<Void>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.builder().timestamp(LocalDateTime.now().toString()).build())
                    .build();
        }
    }
}
