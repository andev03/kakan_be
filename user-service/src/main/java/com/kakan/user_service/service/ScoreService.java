package com.kakan.user_service.service;

import com.kakan.user_service.dto.SubjectScoreDto;
import com.kakan.user_service.dto.response.BlockScoreResponeDto;
import com.kakan.user_service.dto.response.CalculateScoreDto;
import com.kakan.user_service.dto.response.ViewScoreDetail;

import java.util.List;

public interface ScoreService {
    public double calculateGpa( List<SubjectScoreDto> dtos);
    public List<ViewScoreDetail> getScoreDetails() ;
    public List<BlockScoreResponeDto> calculateBlockScore(List<SubjectScoreDto> dtos) ;
    public CalculateScoreDto calculateScore(List<SubjectScoreDto> dtos);


}
