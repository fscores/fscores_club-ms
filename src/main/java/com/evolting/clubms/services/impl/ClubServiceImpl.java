package com.evolting.clubms.services.impl;

import com.evolting.clubms.dtos.request.ClubSearchDto;
import com.evolting.clubms.entities.Club;
import com.evolting.clubms.feigns.PlayerFeignClient;
import com.evolting.clubms.helpers.ClubSpecifications;
import com.evolting.clubms.repositories.ClubRepository;
import com.evolting.clubms.services.ClubService;
import com.evolting.clubms.utils.constants.ResponseMessages;
import com.evolting.clubms.utils.mappers.ClubMapper;
import com.evolting.commonutils.requests.players.PlayerRequestDto;
import com.evolting.commonutils.responses.ApiResponseDto;
import com.evolting.commonutils.responses.Metadata;
import com.evolting.commonutils.responses.clubs.ClubResponseDto;
import com.evolting.commonutils.requests.clubs.ClubRequestDto;
import com.evolting.commonutils.responses.players.PlayerResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;
    private final PlayerFeignClient playerFeignClient;
    private final CacheManager cacheManager;

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
                    .meta(Metadata.basic())
                    .build();

        } catch (EntityNotFoundException e) {
            log.warn("Club not found: {}", e.getMessage());
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();

        } catch (Exception e) {
            log.error("Unexpected error retrieving club with ID {}", id, e);
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
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
                    .meta(Metadata.basic())
                    .build();
        }
    }

    @CachePut(value = "clubs", key = "#result.data.id")
    @Override
    public ApiResponseDto<ClubResponseDto> createClub(ClubRequestDto clubRequestDto) {
        try {
            Club club = clubRepository.save(ClubMapper.toEntity(clubRequestDto));
            ClubResponseDto response = ClubMapper.toResponse(club);

            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(true)
                    .message(ResponseMessages.CREATED)
                    .data(response)
                    .meta(Metadata.basic())
                    .build();

        } catch (Exception e) {
            log.error("Error creating club: {}", clubRequestDto, e);
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();
        }
    }

    @CachePut(value = "clubs", key = "#id")
    @Override
    public ApiResponseDto<ClubResponseDto> updateClub(Integer id, ClubRequestDto clubRequestDto) {
        try {
            if (!clubRepository.existsById(id)) {
                return ApiResponseDto.<ClubResponseDto>builder()
                        .success(false)
                        .message(ResponseMessages.NOT_FOUND + " Club with ID: " + id)
                        .data(null)
                        .meta(Metadata.basic())
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
                    .meta(Metadata.basic())
                    .build();

        } catch (Exception e) {
            log.error("Error updating club with ID {}", id, e);
            return ApiResponseDto.<ClubResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();
        }
    }

    @CacheEvict(value = "clubs", key = "#id")
    @Override
    public ApiResponseDto<Void> deleteClub(Integer id) {
        try {
            if (!clubRepository.existsById(id)) {
                return ApiResponseDto.<Void>builder()
                        .success(false)
                        .message("Club not found with ID: " + id)
                        .data(null)
                        .meta(Metadata.basic())
                        .build();
            }

            clubRepository.deleteById(id);

            return ApiResponseDto.<Void>builder()
                    .success(true)
                    .message(ResponseMessages.DELETED)
                    .data(null)
                    .meta(Metadata.basic())
                    .build();

        } catch (Exception e) {
            log.error("Error deleting club with ID {}", id, e);
            return ApiResponseDto.<Void>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();
        }
    }

    @CachePut(value = "clubs", key = "#clubId")
    @Override
    @Transactional
    public ApiResponseDto<PlayerResponseDto> createPlayerAndAddToClub(Integer clubId, PlayerRequestDto dto) {
        try {
            Club club = clubRepository.findById(clubId)
                    .orElseThrow(() -> new EntityNotFoundException("Club not found with ID: " + clubId));

            // 1. Call player-ms to create a new player
            ApiResponseDto<PlayerResponseDto> createdPlayerResp = playerFeignClient.createPlayer(dto).getBody();

            if (createdPlayerResp == null || !createdPlayerResp.isSuccess() || createdPlayerResp.getData() == null) {
                throw new RuntimeException("Player service failed: " + (createdPlayerResp != null ? createdPlayerResp.getMessage() : "Null response"));
            }

            Integer newPlayerId = createdPlayerResp.getData().getId();

            // 2. Add playerId to club
            club.getPlayerIds().add(newPlayerId);
            clubRepository.save(club);

            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(true)
                    .message("Player created and added to club successfully.")
                    .data(createdPlayerResp.getData())
                    .meta(Metadata.basic())
                    .build();

        } catch (EntityNotFoundException e) {
            log.warn("Club not found: {}", e.getMessage());

            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();

        } catch (Exception e) {
            log.error("Unexpected error creating player for club {}: {}", clubId, e.getMessage(), e);

            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(false)
                    .message("Unable to create player and add to club: " + e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();
        }
    }

    @CachePut(value = "clubs", key = "#clubId")
    @Override
    @Transactional
    public ApiResponseDto<PlayerResponseDto> addExistingPlayerToClub(Integer clubId, Integer playerId) {
        try {
            Club club = clubRepository.findById(clubId)
                    .orElseThrow(() -> new EntityNotFoundException("Club not found with ID: " + clubId));

            // 1. Validate player exists via Feign
            ApiResponseDto<PlayerResponseDto> playerResp = playerFeignClient.getPlayerById(playerId).getBody();

            if (playerResp == null || !playerResp.isSuccess() || playerResp.getData() == null) {
                throw new EntityNotFoundException("Player not found with ID: " + playerId);
            }

            // 2. Add playerId to club (if not already added)
            club.getPlayerIds().add(playerId);
            clubRepository.save(club);

            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(true)
                    .message("Existing player added to club successfully.")
                    .data(playerResp.getData())
                    .meta(Metadata.basic())
                    .build();

        } catch (EntityNotFoundException e) {
            log.warn("Entity not found: {}", e.getMessage());

            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();

        } catch (Exception e) {
            log.error("Unexpected error adding existing player {} to club {}: {}", playerId, clubId, e.getMessage(), e);

            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(false)
                    .message("Unable to add existing player to club: " + e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();
        }
    }

    @CachePut(value = "clubs", key = "#newClubId")
    @Transactional
    public ApiResponseDto<PlayerResponseDto> changePlayerClub(Integer playerId, Integer newClubId) {
        try {
            // 1. Fetch new club
            Club newClub = clubRepository.findById(newClubId)
                    .orElseThrow(() -> new EntityNotFoundException("Club not found with ID: " + newClubId));

            // 2. Fetch player info from player-ms via Feign
            ResponseEntity<ApiResponseDto<PlayerResponseDto>> response = playerFeignClient.getPlayerById(playerId);
            ApiResponseDto<PlayerResponseDto> playerResp = response.getBody();

            if (playerResp == null || !playerResp.isSuccess() || playerResp.getData() == null) {
                throw new EntityNotFoundException("Player not found with ID: " + playerId);
            }

            // 3. Remove player from old club if they are already in one
            Optional<Club> oldClubOpt = clubRepository.findByPlayerIdsContaining(playerId);
            if (oldClubOpt.isPresent()) {
                Club oldClub = oldClubOpt.get();
                if (!oldClub.getId().equals(newClubId)) {
                    oldClub.getPlayerIds().remove(playerId);
                    clubRepository.save(oldClub);

                    // Programmatically update old club cache
                    if (cacheManager.getCache("clubs") != null) {
                        cacheManager.getCache("clubs").put(oldClub.getId(), oldClub);
                    }
                } else {
                    // Player is already in the target club
                    return ApiResponseDto.<PlayerResponseDto>builder()
                            .success(false)
                            .message("Player is already in this club")
                            .data(playerResp.getData())
                            .meta(Metadata.basic())
                            .build();
                }
            }

            // 4. Add player to new club
            newClub.getPlayerIds().add(playerId);
            clubRepository.save(newClub);

            // 5. Return success response
            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(true)
                    .message("Player club changed successfully.")
                    .data(playerResp.getData())
                    .meta(Metadata.basic())
                    .build();

        } catch (EntityNotFoundException e) {
            log.warn("Entity not found: {}", e.getMessage());
            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();
        } catch (Exception e) {
            log.error("Unexpected error changing player {} to club {}: {}", playerId, newClubId, e.getMessage(), e);
            return ApiResponseDto.<PlayerResponseDto>builder()
                    .success(false)
                    .message("Unable to change player club: " + e.getMessage())
                    .data(null)
                    .meta(Metadata.basic())
                    .build();
        }
    }

}
