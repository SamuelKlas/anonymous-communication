package cz.cvut.fel.AnonymousCommunication.DTO;

import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import lombok.Data;

import java.util.List;

@Data
public class SubjectGetDTO {
    private String code;
    private String title;
    private String description;
    private List<User> teachers;

    public SubjectGetDTO(Subject subject) {
        this.code = subject.getCode();
        this.title = subject.getTitle();
        this.description = subject.getDescription();
        this.teachers = subject.getTeachers();
    }
}
