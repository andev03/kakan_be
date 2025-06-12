package com.kakan.school_service.controller;

import com.kakan.school_service.dto.response.ResponseDto;
import com.kakan.school_service.pojo.UniversityDocument;
import com.kakan.school_service.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/university")
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    @GetMapping("/search")
    public ResponseEntity<ResponseDto> getUniversityByName(@RequestParam String name) {
        UniversityDocument university = universityService.getUniversityByName(name);
        if (university != null) {
            ResponseDto<UniversityDocument> response = ResponseDto.<UniversityDocument>builder()
                    .status(200)
                    .message("University found")
                    .data(university)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(ResponseDto.<UniversityDocument>builder()
                    .status(404)
                    .message("University not found")
                    .data(null)
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllUniversities() {
        return ResponseEntity.ok(ResponseDto.<List<UniversityDocument>>builder()
                .status(200)
                .message("All universities retrieved successfully")
                .data(universityService.getAllUniversities())
                .build());
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteUniversityByName(@RequestParam String name) {
        boolean result = universityService.deleteUniversityByName(name);
        if (result) {
            return ResponseEntity.ok(ResponseDto.<String>builder()
                    .status(200)
                    .message("University deleted successfully")
                    .data("University " + name + " deleted")
                    .build());
        } else {
            return ResponseEntity.status(404).body(ResponseDto.<String>builder()
                    .status(404)
                    .message("University not found")
                    .data(null)
                    .build());

        }
    }

    @PutMapping
    public ResponseEntity<ResponseDto> deleteUniversityByName(@RequestBody UniversityDocument universityDocument) {
        boolean result = universityService.updateUniversity(universityDocument);
        if (result) {
            return ResponseEntity.ok(ResponseDto.<String>builder()
                    .status(200)
                    .message("University deleted successfully")
                    .data(null)
                    .build());
        } else {
            return ResponseEntity.status(404).body(ResponseDto.<String>builder()
                    .status(404)
                    .message("University not found")
                    .data(null)
                    .build());

        }
    }
}
