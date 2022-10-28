package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.SubjectRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SubjectServiceTest {
    @MockBean
    SubjectRepository subjectRepository;

    @Autowired
    SubjectService subjectService;

    public User generateUser(){
        return new User("123","123");
    }

    @Test
    public void addStudentToSubject_StudentDoesNotBelong_SuccessfullyAdded(){
        User user = generateUser();
        Subject subject = new Subject("B201","Placeholder subject","Pld");
        subjectService.addStudentToSubject(user,subject);

        Assertions.assertEquals(1,subject.getStudents().size());

    }

    @Test
    public void addStudentToSubject_StudentAlreadyBelongs_NotAddedAgain(){
        User user = generateUser();
        Subject subject = new Subject("B201","Placeholder subject","Pld");
        subjectService.addStudentToSubject(user,subject);
        Assertions.assertEquals(1,subject.getStudents().size());
        subjectService.addStudentToSubject(user,subject);
        Assertions.assertEquals(1,subject.getStudents().size());

    }

    @Test
    public void addTeacherToSubject_TeacherDoesNotBelong_SuccessfullyAdded(){
        User user = generateUser();
        Subject subject = new Subject("B201","Placeholder subject","Pld");
        subjectService.addStudentToSubject(user,subject);
        Assertions.assertEquals(1,subject.getStudents().size());

    }
}
