package com.kakan.user_service.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "block")
@Getter
@Setter
public class Block {

    @Id
    private String code;

    @ManyToMany
    @JoinTable(
            name               = "block_subject",
            joinColumns        = @JoinColumn(name = "block_code"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();

}
