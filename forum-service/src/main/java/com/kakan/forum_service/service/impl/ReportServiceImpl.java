package com.kakan.forum_service.service.impl;

import com.kakan.forum_service.dto.ReportDto;
import com.kakan.forum_service.dto.request.CreateReportRequestDto;
import com.kakan.forum_service.exception.PostNotFoundException;
import com.kakan.forum_service.mapper.ReportMapper;
import com.kakan.forum_service.pojo.Post;
import com.kakan.forum_service.pojo.Report;
import com.kakan.forum_service.repository.PostRepository;
import com.kakan.forum_service.repository.ReportRepository;
import com.kakan.forum_service.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportServiceImpl implements ReportService {

    final ReportMapper reportMapper;

    final ReportRepository reportRepository;

    final PostRepository postRepository;

    @Override
    @Transactional
    public ReportDto createReport(UUID postId, CreateReportRequestDto createReportRequestDto) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        Report report = reportRepository.save(
                Report.builder()
                        .post(post)
                        .reporterId(createReportRequestDto.getAccountId())
                        .reason(createReportRequestDto.getReason())
                        .build()
        );
        return reportMapper.toDto(report);
    }

    @Override
    public List<ReportDto> viewReportByPostId(UUID postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        List<Report> reports = reportRepository.findByPostId(post.getId());
        return reportMapper.toDtoList(reports);
    }

    @Override
    public List<ReportDto> viewAllReport() {
        List<Report> reports = reportRepository.findAll();

        return reportMapper.toDtoList(reports);
    }
}
