package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice extends AbstractEntity implements Serializable {

    @JsonIgnore
    @ManyToOne
    private Question question;

    private String content;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean correct;

    public Choice(Question question,Choice choice) {
        this.question = question;
        this.content = choice.content;
        this.correct = choice.correct;
    }
}
