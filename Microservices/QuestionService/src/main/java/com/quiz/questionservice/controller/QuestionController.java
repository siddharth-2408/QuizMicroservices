package com.quiz.questionservice.controller;

import com.quiz.questionservice.model.QuestionWrapper;
import com.quiz.questionservice.model.Questions;
import com.quiz.questionservice.model.Response;
import com.quiz.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allquestions")
    public ResponseEntity<List<Questions>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Questions>> getAllQuestionsByCategory(@PathVariable String category) {
        return questionService.getAllQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Questions question) {
        return questionService.addQuestion(question);
    }

    // Generate
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsId(@RequestParam String category,
                                              @RequestParam int num) {
        return questionService.getQuestionsId(category,num);
    }

    // Get questions based on question id
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsForQuizWithId(questionIds);
    }

    // score
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionService.calculateScore(responses);
    }
}
