package com.evolting.clubms.controllers;

import com.evolting.clubms.dtos.request.ClubSearchDto;
import com.evolting.clubms.services.ClubService;
import com.evolting.commonutils.requests.clubs.ClubRequestDto;
import com.evolting.commonutils.requests.players.PlayerRequestDto;
import com.evolting.commonutils.responses.ApiResponseDto;
import com.evolting.commonutils.responses.clubs.ClubResponseDto;
import com.evolting.commonutils.responses.players.PlayerResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club")
@AllArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @PostMapping("/search")
    public ResponseEntity<ApiResponseDto<List<ClubResponseDto>>> searchClub(
            @RequestBody ClubSearchDto clubSearchDto,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        ApiResponseDto<List<ClubResponseDto>> response =
                clubService.searchClub(clubSearchDto, pageNo, pageSize, sortBy);

        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ClubResponseDto>> getClubById(@PathVariable Integer id) {
        ApiResponseDto<ClubResponseDto> response = clubService.getClubById(id);

        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(response);
    }


    @PostMapping
    public ResponseEntity<ApiResponseDto<ClubResponseDto>> createClub(
            @Valid @RequestBody ClubRequestDto request
    ) {
        ApiResponseDto<ClubResponseDto> response = clubService.createClub(request);

        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }


    @PutMapping
    public ResponseEntity<ApiResponseDto<ClubResponseDto>> updateClub(
            @Valid @RequestBody ClubRequestDto request
    ) {
        ApiResponseDto<ClubResponseDto> response =
                clubService.updateClub(request.getId(), request);

        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/{clubId}/players/new")
    public ResponseEntity<ApiResponseDto<PlayerResponseDto>> createPlayerAndAddToClub(
            @PathVariable Integer clubId,
            @Valid @RequestBody PlayerRequestDto request
    ) {
        ApiResponseDto<PlayerResponseDto> response =
                clubService.createPlayerAndAddToClub(clubId, request);

        HttpStatus status = response.isSuccess()
                ? HttpStatus.CREATED
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/{clubId}/players/{playerId}")
    public ResponseEntity<ApiResponseDto<PlayerResponseDto>> addExistingPlayerToClub(
            @PathVariable Integer clubId,
            @PathVariable Integer playerId
    ) {
        ApiResponseDto<PlayerResponseDto> response =
                clubService.addExistingPlayerToClub(clubId, playerId);

        HttpStatus status = response.isSuccess()
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }


    @PostMapping("/{newClubId}/players/{playerId}/change")
    public ResponseEntity<ApiResponseDto<PlayerResponseDto>> changePlayerClub(
            @PathVariable Integer playerId,
            @PathVariable Integer newClubId
    ) {
        ApiResponseDto<PlayerResponseDto> response =
                clubService.changePlayerClub(playerId, newClubId);

        HttpStatus status = response.isSuccess()
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(response);
    }
}
