package com.evolting.clubms.controllers;

import com.evolting.clubms.dtos.request.ClubRequestDto;
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

    @GetMapping
    public ResponseEntity<?> getAllClubs(@RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(clubService.getAllClubs(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClubById(@PathVariable Integer id) {
        ClubResponseDto ret = clubService.getClubById(id);
        if (ret == null) {
            return new ResponseEntity<>("Club not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClub(@Valid @RequestBody ClubRequestDto clubRequestDto) {
        ClubResponseDto ret = clubService.createClub(clubRequestDto);
        if (ret == null) {
            return new ResponseEntity<>("Fail to register club data.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateClub(@Valid @RequestBody ClubRequestDto clubRequestDto) {
        ClubResponseDto ret = clubService.updateClub(clubRequestDto.getId(), clubRequestDto);
        if (ret == null) {
            return new ResponseEntity<>("Fail to update club data.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
