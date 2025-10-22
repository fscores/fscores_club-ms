package com.evolting.clubms.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubResponseDto {
    private Integer id;
    private String name;
    private Integer foundedYear;
    private String avatarUrl;
    private List<Integer> playerIds;
}
