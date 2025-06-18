package com.kakan.forum_service.service;

import com.kakan.forum_service.dto.ReportDto;
import com.kakan.forum_service.dto.request.CreateReportRequestDto;
import com.kakan.forum_service.pojo.Report;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    ReportDto createReport(UUID postId, CreateReportRequestDto createReportRequestDto);

    List<ReportDto> viewReportByPostId(UUID postId);

    List<ReportDto> viewAllReport();
}
