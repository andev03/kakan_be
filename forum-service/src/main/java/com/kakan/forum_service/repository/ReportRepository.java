package com.kakan.forum_service.repository;

import com.kakan.forum_service.pojo.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findByPostId(UUID postId);
}
