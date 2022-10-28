package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Chat;
import cz.cvut.fel.AnonymousCommunication.Model.Quiz;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.ChatRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.SubjectRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@Data
@AllArgsConstructor
public class SubjectService {

    private SubjectRepository subjectRepository;
    private UserRepository uRep;
    private ChatRepository chatRepository;


    public Subject saveSubject(Subject subject){
        boolean alreadyExists = exists(subject);

        if(!alreadyExists){
            log.info("Saving subject with code {} to the database", subject.getCode());
            return subjectRepository.save(subject);
        }
        else{
            log.info("Subject with code {} already exists",subject.getCode());
            return null;
        }
    }



    public boolean exists(Subject subject){
        Subject found;
        try {
            found = subjectRepository.findSubjectByCode(subject.getCode());
        }catch(UsernameNotFoundException e){
            return false;
        }
        return found != null;

    }

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

    public Optional<Subject> findById(long id){
        return subjectRepository.findById(id);
    }

    public void addStudentToSubject(User student, Subject subject){
        log.info("Adding student {} to subject {}",student.getUsername(),subject.getCode());
        student.addStudiedSubject(subject);
        subject.addStudent(student);

    }

    public void addTeacherToSubject(User teacher, Subject subject){
        log.info("Adding teacher {} to subject {}",teacher.getUsername(),subject.getCode());
        teacher.addTaughtSubject(subject);
        subject.addTeacher(teacher);

    }

    public void removeTeacherFromSubject(User teacher, Subject subject){
        log.info("Removing teacher {} from subject {}",teacher.getUsername(),subject.getCode());
        teacher.removeTaughtSubject(subject);
        subject.removeTeacher(teacher);

    }

    public void removeStudentFromSubject(User student, Subject subject){
        log.info("Removing student {} from subject {}",student.getUsername(),subject.getCode());
        student.removeStudiedSubject(subject);
        subject.removeStudent(student);

    }

    public void addQuiz(Subject subject, Quiz quiz){
            subject.getQuizzes().add(quiz);
            quiz.setSubject(subject);
    }

    public void addChat(Subject subject, Chat chat){
        subject.getChats().add(chat);
        subjectRepository.save(subject);
        
    }


}
