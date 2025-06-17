package com.kakan.user_service.controller;

import com.kakan.user_service.dto.SubjectScoreDto;
import com.kakan.user_service.dto.request.ScoreRequest;
import com.kakan.user_service.dto.response.GpaResponseDto;
import com.kakan.user_service.dto.response.ResponseDto;
import com.kakan.user_service.dto.response.ViewScoreDetail;
import com.kakan.user_service.pojo.Score;
import com.kakan.user_service.service.ScoreService;
import jakarta.validation.Valid;
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

    @PostMapping(value = "/calculate/gpa/{accountId}")
    public ResponseDto<GpaResponseDto> submitScore(@PathVariable int accountId, @RequestBody @Valid ScoreRequest dto) {
        double gpa = scoreService.calculateGpa(accountId, dto.getSubjectScores());
        GpaResponseDto responseDto = new GpaResponseDto(gpa);
        return new ResponseDto<>(200, "GPA calculated successfully", responseDto);

    }

    @GetMapping(value = "/view/gpa/{accountId}")
    public ResponseDto<List<ViewScoreDetail>> viewScoreDetail(@PathVariable int accountId) {
        List<ViewScoreDetail> viewScoreDetail = scoreService.getScoreDetails(accountId);
        return new ResponseDto<>(200, "Score details fetched successfully", viewScoreDetail);
    }
}
