package com.evolting.clubms.feigns;

import com.evolting.commonutils.requests.players.PlayerRequestDto;
import com.evolting.commonutils.responses.ApiResponseDto;
import com.evolting.commonutils.responses.players.PlayerResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "player-ms")
public interface PlayerFeignClient {

    @PostMapping("/player")
    ResponseEntity<ApiResponseDto<PlayerResponseDto>> createPlayer(@Valid @RequestBody PlayerRequestDto playerRequestDto);

    @GetMapping("/player/{id}")
    ResponseEntity<ApiResponseDto<PlayerResponseDto>> getPlayerById(@PathVariable Integer id);
}

