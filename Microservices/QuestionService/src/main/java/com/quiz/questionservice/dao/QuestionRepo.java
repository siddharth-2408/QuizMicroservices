package com.quiz.questionservice.dao;


import com.quiz.questionservice.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Questions, Integer> {
    List<Questions> findQuestionsByCategory(String category);

    @Query(value = "SELECT q.id FROM questions q WHERE q.category = :category ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int limit);

}
