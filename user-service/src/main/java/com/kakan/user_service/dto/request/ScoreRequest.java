package com.kakan.user_service.dto.request;

import com.kakan.user_service.dto.SubjectScoreDto;
import lombok.Data;

import java.util.List;

@Data
public class ScoreRequest {
    List<SubjectScoreDto> subjectScores;
}
