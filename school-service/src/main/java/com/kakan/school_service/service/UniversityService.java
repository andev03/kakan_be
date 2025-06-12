package com.kakan.school_service.service;

import com.kakan.school_service.pojo.UniversityDocument;

import java.util.List;

public interface UniversityService {
    UniversityDocument getUniversityByName(String name);

    List<UniversityDocument> getAllUniversities();

    boolean deleteUniversityByName(String name);

    boolean updateUniversity(UniversityDocument universityDocument);
}
