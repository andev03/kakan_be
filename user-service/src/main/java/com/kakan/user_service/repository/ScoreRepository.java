package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    List<Score> findScoreByAccount_Id(int accountId);
    boolean existsByAccount_Id(int accountId);
}
