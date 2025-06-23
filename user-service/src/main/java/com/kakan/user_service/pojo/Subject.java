package com.kakan.user_service.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject")
@Getter
@Setter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_name", nullable = false, unique = true, length = 100)
    private String subjectName;

    @ManyToMany(mappedBy = "subjects")
    private Set<Block> blocks = new HashSet<>();
}
