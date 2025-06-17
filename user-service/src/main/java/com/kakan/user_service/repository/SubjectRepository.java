package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findSubjectBySubjectId(Integer subjectId);
}
