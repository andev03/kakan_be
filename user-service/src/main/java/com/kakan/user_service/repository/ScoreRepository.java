package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
