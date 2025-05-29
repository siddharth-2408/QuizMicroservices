package com.quiz.questionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String difficultylevel;

    @Column(columnDefinition = "TEXT")
    private String questiontitle;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String rightanswer;
}