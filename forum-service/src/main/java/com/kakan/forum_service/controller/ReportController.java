package com.kakan.forum_service.controller;

import com.kakan.forum_service.dto.request.CreateReportRequestDto;
import com.kakan.forum_service.dto.response.ResponseDto;
import com.kakan.forum_service.service.ReportService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@AllArgsConstructor
@RequestMapping("/api")
public class ReportController {

    final ReportService reportService;

    @PostMapping("/report/{postId}")
    public ResponseDto<Object> createReport(@PathVariable UUID postId, @Valid @RequestBody CreateReportRequestDto createReportRequestDto) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(reportService.createReport(postId, createReportRequestDto))
                .build();
    }


    @GetMapping("/reports/{postId}")
    public ResponseDto<Object> viewReportByPostId(@PathVariable UUID postId) {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(reportService.viewReportByPostId(postId))
                .build();
    }

    @GetMapping("/reports")
    public ResponseDto<Object> viewAllReport() {
        return ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(reportService.viewAllReport())
                .build();
    }
}
