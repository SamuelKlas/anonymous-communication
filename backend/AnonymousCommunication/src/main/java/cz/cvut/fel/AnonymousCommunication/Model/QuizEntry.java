package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fel.AnonymousCommunication.DTO.QuizEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizEntry extends AbstractEntity{
    @JsonIgnore
    @ManyToOne
    private Quiz quiz;

    @OneToMany(mappedBy = "quizEntry",cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @ElementCollection
    private List<Boolean> correctness = new ArrayList<>();

    private int achievedPoints = 0;
    private int correctAnswers = 0;

    public void evaluate(){
        achievedPoints = 0;
        correctAnswers = 0;
        for (Answer answer: answers) {
            if(answer.getQuestion().getChoices().get(answer.getAnswerIdx()).isCorrect()){
                int points = answer.getQuestion().getPoints();
                achievedPoints += points;
                correctAnswers++;
                correctness.add(true);
            }
            else{
                correctness.add(false);
            }
        }
    }

    public QuizEntry(QuizEntryDTO quizEntryDTO) {
        this.answers = quizEntryDTO.getAnswers().stream()
                .map(answer -> new Answer(this,answer))
                .collect(Collectors.toList());
        this.correctAnswers = 0;
        this.achievedPoints = 0;
    }
}
