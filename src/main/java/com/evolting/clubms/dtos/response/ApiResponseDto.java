package com.evolting.clubms.dtos.response;

import com.evolting.clubms.helpers.Metadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDto<T> {
    private boolean success;
    private String message;
    private T data;
    private Metadata meta;
}
