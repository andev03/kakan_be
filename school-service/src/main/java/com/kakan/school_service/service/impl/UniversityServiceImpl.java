package com.kakan.school_service.service.impl;

import com.kakan.school_service.pojo.UniversityDocument;
import com.kakan.school_service.repository.UniversityRepository;
import com.kakan.school_service.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public UniversityDocument getUniversityByName(String name) {
        UniversityDocument universityDocument = universityRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("University not found: " + name));
        return universityDocument;
    }

    @Override
    public List<UniversityDocument> getAllUniversities() {
        return universityRepository.findAll();
    }

    @Override
    public boolean deleteUniversityByName(String name) {
        boolean result = false;
        UniversityDocument universityDocument = universityRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("University not found: " + name));
        if (universityDocument != null) {
            universityRepository.delete(universityDocument);
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateUniversity(UniversityDocument universityDocument) {
        boolean result = false;
    UniversityDocument existingUniversity = universityRepository.findByName(universityDocument.getName())
            .orElseThrow(() -> new RuntimeException("University not found: " + universityDocument.getName()));
        if (existingUniversity != null) {
            universityRepository.save(universityDocument);
            return true;
        }
        return false;
    }
}
