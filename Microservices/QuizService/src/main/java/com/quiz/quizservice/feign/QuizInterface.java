package com.quiz.quizservice.feign;

import com.quiz.quizservice.model.QuestionWrapper;
import com.quiz.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTIONSERVICE")
public interface QuizInterface {

    //What methods are required to call from QuestionService.. Just mention here

    // Generate
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsId(@RequestParam String category,
                                                        @RequestParam int num);

    // Get questions based on question id
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds);

    // score
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
