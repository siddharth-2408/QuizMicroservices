package com.quiz.questionservice.service;

import com.quiz.questionservice.dao.QuestionRepo;
import com.quiz.questionservice.model.QuestionWrapper;
import com.quiz.questionservice.model.Questions;
import com.quiz.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<List<Questions>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Questions>> getAllQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionRepo.findQuestionsByCategory(category), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Questions question) {
        questionRepo.save(question);
        try{
            return new ResponseEntity<>("Question added", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not Added", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionsId(String category, int num) {
        List<Integer> questions = questionRepo.findRandomQuestionsByCategory(category, num);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuizWithId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        List<Questions> questions = new ArrayList<>();

        for(Integer id : questionIds){
            questions.add(questionRepo.findById(id).get());
        }

        for(Questions q : questions){
            wrappers.add(new QuestionWrapper(
                    q.getId(),
                    q.getQuestiontitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            ));
        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScore(List<Response> responses) {
        int result = 0;

        for(Response r : responses) {
            Questions q = questionRepo.findById(r.getId()).get();
            if(r.getResponse().equals(q.getRightanswer()))
                result++;
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
