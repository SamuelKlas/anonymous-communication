package cz.cvut.fel.AnonymousCommunication.DTO;

import cz.cvut.fel.AnonymousCommunication.Model.Question;
import lombok.Data;

import java.util.List;
@Data
public class QuizDTO {
    private String title;
    private List<Question> questions;
}
