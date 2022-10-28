package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question extends AbstractEntity implements Serializable {

    @JsonIgnore
    @ManyToOne
    private Quiz quiz;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private List<Choice> choices = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    private String content;

    private int points;

    public Question(Quiz quiz,Question question) {
        this.quiz = quiz;
        this.content = question.content;
        this.points = question.points;
        for (Choice choice: question.choices) {
            this.choices.add(new Choice(this,choice));
        }
    }
}
