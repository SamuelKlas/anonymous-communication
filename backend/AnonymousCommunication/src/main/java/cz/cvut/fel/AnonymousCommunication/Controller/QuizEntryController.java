package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.DTO.QuizEntryDTO;
import cz.cvut.fel.AnonymousCommunication.Model.Quiz;
import cz.cvut.fel.AnonymousCommunication.Model.QuizEntry;
import cz.cvut.fel.AnonymousCommunication.Service.QuizEntryService;
import cz.cvut.fel.AnonymousCommunication.Service.QuizService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/quizzes")
@AllArgsConstructor
public class QuizEntryController {

    private QuizService quizService;
    private QuizEntryService quizEntryService;

    @GetMapping("/{quizId}/quizEntries")
    public ResponseEntity findQuizEntriesForQuiz(@PathVariable long quizId){
        Quiz quiz = quizService.findById(quizId);
        return ResponseEntity.ok().body(quizEntryService.findAll(quiz));
    }

    @PostMapping("/{quizId}/quizEntries")
    public ResponseEntity submitQuizEntry(@PathVariable long quizId,
                                          @RequestBody QuizEntryDTO quizEntryDTO){
        Quiz quiz = quizService.findById(quizId);
        QuizEntry quizEntry = new QuizEntry(quizEntryDTO);
        quizEntry.setQuiz(quiz);
        quizEntryService.save(quizEntry);
        quizEntryService.pairQuestionsAndAnswers(quiz,quizEntry);
        quizEntryService.evaluateQuizEntry(quizEntry);
        quizService.generateStatistics(quiz);
        return ResponseEntity.ok().body(quizEntryService.save(quizEntry));
    }

}
