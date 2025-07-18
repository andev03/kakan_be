package com.kakan.user_service.controller;

import com.kakan.user_service.dto.SubjectScoreDto;
import com.kakan.user_service.dto.request.ScoreRequest;
import com.kakan.user_service.dto.response.*;
import com.kakan.user_service.pojo.Score;
import com.kakan.user_service.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CalculateController {
    final ScoreService scoreService;

    public CalculateController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

//    @PostMapping(value = "/calculate/gpa")
//    public ResponseDto<GpaResponseDto> submitScore(@RequestBody @Valid ScoreRequest dto) {
//        double gpa = scoreService.calculateGpa(dto.getSubjectScores());
//        GpaResponseDto responseDto = new GpaResponseDto(gpa);
//        return new ResponseDto<>(200, "GPA calculated successfully", responseDto);
//
//    }


    @GetMapping(value = "/view/scoreDetail")
    public ResponseDto<List<ViewScoreDetail>> viewScoreDetail() {
        List<ViewScoreDetail> viewScoreDetail = scoreService.getScoreDetails();
        return new ResponseDto<>(HttpStatus.OK.value(), "Score details fetched successfully", viewScoreDetail);
    }

//    @PostMapping(value = "/calculate/block-score")
//    public ResponseDto<List<BlockScoreResponeDto>> calculateBlockScore(@RequestBody @Valid ScoreRequest dto) {
//        List<BlockScoreResponeDto> blockScores = scoreService.calculateBlockScore(dto.getSubjectScores());
//        return new ResponseDto<>(200, "Block scores calculated successfully", blockScores);
//    }

        @PostMapping(value = "/calculate/score")
    public ResponseDto<CalculateScoreDto> calculateScore(@RequestBody @Valid ScoreRequest dto) {
        CalculateScoreDto score = scoreService.calculateScore(dto.getSubjectScores());
        return new ResponseDto<>(HttpStatus.OK.value(), "Scores calculated successfully", score);
    }
}
