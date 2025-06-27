package com.kakan.user_service.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CalculateScoreDto {
    private double Gpa;
    private List<BlockScoreResponeDto> blockScores;
}
