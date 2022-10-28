package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.*;
import cz.cvut.fel.AnonymousCommunication.Repository.ChoiceRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.QuestionRepository;
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
public class QuizService {
    private QuizRepository quizRepository;
    private QuizEntryRepository quizEntryRepository;
    private QuestionRepository questionRepository;
    private ChoiceRepository choiceRepository;

    public List<Quiz> findAll(){
        return quizRepository.findAll();
    }

    public List<Question> findAllQuestions(){
        return questionRepository.findAll();
    }

    public List<Choice> findAllChoices(){
        return choiceRepository.findAll();
    }

    public Quiz findById(long id){
        return quizRepository.findById(id).get();
    }

    public Question findQuestionById(long id){
        return questionRepository.findById(id).get();
    }

    public Choice findChoiceById(long id){
        return choiceRepository.findById(id).get();
    }


    public Quiz saveQuiz(Quiz quiz){
        return quizRepository.save(quiz);
    }

    public List<Quiz> saveQuiz(List<Quiz> quizzes){
        return quizRepository.saveAll(quizzes);
    }

    public Question addQuestion(Quiz quiz,Question question){
        log.info("Adding question {} to quiz {}",question.getContent(),quiz.getId());
        //question = questionRepository.save(question);
        quiz.getQuestions().add(question);
        question.setQuiz(quiz);
        return question;
    }

    public List<Question> addQuestions(Quiz quiz, List<Question> questions){
        questions.forEach(question -> addQuestion(quiz,question));
        return questions;
    }

    public void removeQuestion(Quiz quiz,Question question){
        log.info("Removing question {} from quiz {}",question.getContent(),quiz.getId());
        if(quiz.getQuestions().contains(question)){
            quiz.getQuestions().remove(question);
            questionRepository.delete(question);
        }
    }

    public void removeQuestions(Quiz quiz, List<Question> questions){
        questions.forEach(question -> removeQuestion(quiz,question));
    }

    public void generateStatistics(Quiz quiz){
        log.info("Generating statistics for quiz {}",quiz.getId());
        quiz.generateStatistics();
    }

    public Choice addChoice(Question question, Choice choice){
        log.info("Adding choice {} to question {}",choice.getContent(),question.getContent());
        choiceRepository.save(choice);
        question.getChoices().add(choice);
        choice.setQuestion(question);
        return choice;
    }

    public List<Choice> addChoices(Question question, List<Choice> choices){
        choices.forEach(choice -> addChoice(question,choice));
        return choices;
    }

    public void removeChoice(Question question, Choice choice){
        log.info("Removing choice {} from question {}",choice.getContent(),question.getContent());
        question.getChoices().remove(choice);
        choiceRepository.delete(choice);

    }

    public void removeChoices(Question question, List<Choice> choices){
        choices.forEach(choice -> removeChoice(question,choice));
    }

    public void addQuizEntry(Quiz quiz, QuizEntry quizEntry){
        quiz.getQuizEntries().add(quizEntry);
        quizEntry.setQuiz(quiz);
    }







}
