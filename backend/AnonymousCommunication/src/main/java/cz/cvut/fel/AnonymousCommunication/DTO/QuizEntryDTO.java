package cz.cvut.fel.AnonymousCommunication.DTO;

import cz.cvut.fel.AnonymousCommunication.Model.Answer;
import lombok.Data;

import java.util.List;
@Data
public class QuizEntryDTO {
    private List<Answer> answers;
}
