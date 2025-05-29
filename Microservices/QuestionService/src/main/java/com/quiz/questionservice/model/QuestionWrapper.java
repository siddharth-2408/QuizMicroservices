package com.quiz.questionservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionWrapper {

    private Long id;
    private String questiontitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
