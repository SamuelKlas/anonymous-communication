package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Answer;
import cz.cvut.fel.AnonymousCommunication.Model.Question;
import cz.cvut.fel.AnonymousCommunication.Model.Quiz;
import cz.cvut.fel.AnonymousCommunication.Model.QuizEntry;
import cz.cvut.fel.AnonymousCommunication.Repository.AnswerRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.QuizEntryRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.QuizRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@Data
@AllArgsConstructor
public class QuizEntryService {
    private QuizEntryRepository quizEntryRepository;
    private AnswerRepository answerRepository;
    private QuizRepository quizRepository;

    public QuizEntry save(QuizEntry quizEntry){
        return quizEntryRepository.save(quizEntry);
    }

    public List<QuizEntry> findAll(){
        return quizEntryRepository.findAll();
    }
    public List<QuizEntry> findAll(Quiz quiz){
        return quiz.getQuizEntries();
    }

    public void pairQuestionsAndAnswers(Quiz quiz, QuizEntry quizEntry){
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            Question question = quiz.getQuestions().get(i);
            Answer answer = quizEntry.getAnswers().get(i);
            question.getAnswers().add(answer);
            answer.setQuestion(question);

        }
    }

    public void evaluateQuizEntry(QuizEntry quizEntry){
        quizEntry.evaluate();
        this.save(quizEntry);
    }


}
