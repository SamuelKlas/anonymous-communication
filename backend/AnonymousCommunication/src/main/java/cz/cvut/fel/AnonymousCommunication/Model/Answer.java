package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends AbstractEntity{

    @JsonIgnore
    @ManyToOne
    private Question question;

    @JsonIgnore
    @ManyToOne
    private QuizEntry quizEntry;

    private int answerIdx;

    public Answer(QuizEntry quizEntry,Answer answer) {
        this.quizEntry = quizEntry;
        this.answerIdx = answer.answerIdx;
    }
}
