package com.kakan.school_service.repository;

import com.kakan.school_service.pojo.UniversityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends MongoRepository<UniversityDocument, String> {
    Optional<UniversityDocument> findByName(String name);
}
