package cz.cvut.fel.AnonymousCommunication.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.AnonymousCommunication.DTO.QuizDTO;
import cz.cvut.fel.AnonymousCommunication.Model.Choice;
import cz.cvut.fel.AnonymousCommunication.Model.Question;
import cz.cvut.fel.AnonymousCommunication.Model.Quiz;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Service.QuizService;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Data
@AllArgsConstructor
public class QuizController {
    private QuizService quizService;
    private SubjectService subjectService;

    @GetMapping("/quizzes")
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(quizService.findAll());
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity findById(@PathVariable long quizId){
        return ResponseEntity.ok().body(quizService.findById(quizId));
    }

    @GetMapping("/questions")
    public ResponseEntity findAllQuestions(){
        return ResponseEntity.ok().body(quizService.findAllQuestions());
    }

    @GetMapping("/choices")
    public ResponseEntity findAllChoices(){
        return ResponseEntity.ok().body(quizService.findAllChoices());
    }

    @GetMapping("/subjects/{subjectId}/quizzes")
    public ResponseEntity findAll(@PathVariable long subjectId){
        Subject subject = subjectService.findById(subjectId).get();
        return ResponseEntity.ok().body(subject.getQuizzes());

    }

    @PostMapping("/subjects/{subjectId}/quizzes")
    public ResponseEntity saveQuiz(@PathVariable long subjectId, @RequestBody QuizDTO quizDTO){
        Subject subject = subjectService.findById(subjectId).get();
        Quiz quiz = new Quiz(quizDTO);
        subjectService.addQuiz(subject,quiz);

        quizService.saveQuiz(quiz);

        return ResponseEntity.ok().body(quiz);

    }

    @PostMapping("/quizzes/{quizId}")
    public ResponseEntity addQuestions(@PathVariable long quizId,
                                       @RequestBody List<Question> questions){
        Quiz quiz = quizService.findById(quizId);
        return ResponseEntity.ok().body(quizService.addQuestions(quiz,questions));
    }

    @DeleteMapping("/quizzes/{quizId}")
    public ResponseEntity removeQuestions(@PathVariable long quizId,
                                          @RequestParam("questionIds") List<Long> questionIds){
        Quiz quiz = quizService.findById(quizId);
        List<Question> questions = questionIds.stream().map(
                questionId -> quizService.findQuestionById(questionId)).
                collect(Collectors.toList());
        quizService.removeQuestions(quiz,questions);
        return ResponseEntity.noContent().build();
    }
}
