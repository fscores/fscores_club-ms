package com.evolting.clubms.controllers;

import com.evolting.clubms.dtos.request.ClubRequestDto;
import com.evolting.clubms.dtos.request.ClubSearchDto;
import com.evolting.clubms.dtos.response.ApiResponseDto;
import com.evolting.clubms.dtos.response.ClubResponseDto;
import com.evolting.clubms.services.ClubService;
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
    public ResponseEntity<?> searchPlayer(@RequestBody ClubSearchDto clubSearchDto,
                                          @RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        ApiResponseDto<List<ClubResponseDto>> ret = clubService.searchClub(clubSearchDto, pageNo, pageSize, sortBy);
        if (ret == null) {
            return new ResponseEntity<>("Club not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClubById(@PathVariable Integer id) {
        ApiResponseDto<ClubResponseDto> ret = clubService.getClubById(id);
        if (ret == null) {
            return new ResponseEntity<>("Club not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClub(@Valid @RequestBody ClubRequestDto clubRequestDto) {
        ApiResponseDto<ClubResponseDto> ret = clubService.createClub(clubRequestDto);
        if (ret == null) {
            return new ResponseEntity<>("Fail to register club data.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateClub(@Valid @RequestBody ClubRequestDto clubRequestDto) {
        ApiResponseDto<ClubResponseDto> ret = clubService.updateClub(clubRequestDto.getId(), clubRequestDto);
        if (ret == null) {
            return new ResponseEntity<>("Fail to update club data.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
