package com.quiz.quizservice.service;

import com.quiz.quizservice.dao.QuizDao;
import com.quiz.quizservice.feign.QuizInterface;
import com.quiz.quizservice.model.QuestionWrapper;
import com.quiz.quizservice.model.Quiz;
import com.quiz.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int num, String title) {

        //Call Generate URL -> RestTemplate (Bad Idea). Go through Eureka and OpenFeign
        //URL -> http://localhost:8080/question/generate

        List<Integer> questionId = quizInterface.getQuestionsId(category,num).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionId);

        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz Created", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        List<Integer> questionIds = quizDao.findById(id).get().getQuestionIds();

        ResponseEntity<List<QuestionWrapper>> questionsForQuiz = quizInterface.getQuestionsForQuiz(questionIds);

        return questionsForQuiz;
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}
