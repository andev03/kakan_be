package com.kakan.user_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockScoreResponeDto {
    private String blockCode;
    private double score;
}
