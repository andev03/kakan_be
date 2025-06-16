package com.kakan.user_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SubjectScoreDto {
    private Integer subjectId;
    @Min(value = 0, message = "Score for Year 10 must be at least 0")
    @Max(value = 10, message = "Score for Year 10 must be at most 10")
    private Double scoreYear10;

    @Min(value = 0, message = "Score for Year 11 must be at least 0")
    @Max(value = 10, message = "Score for Year 11 must be at most 10")
    private Double scoreYear11;

    @Min(value = 0, message = "Score for Year 12 must be at least 0")
    @Max(value = 10, message = "Score for Year 12 must be at most 10")
    private Double scoreYear12;
}
