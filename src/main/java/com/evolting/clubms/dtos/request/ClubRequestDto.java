package com.evolting.clubms.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubRequestDto {
    private Integer id;

    @NotBlank(message = "Club name is a mandatory field.")
    @Length(min = 1, max = 50, message = "Club must be between 1 and 50 characters.")
    private String name;
    private Integer foundedYear;
    private List<Integer> playerIds;
}
