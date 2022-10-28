package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.cvut.fel.AnonymousCommunication.DTO.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends AbstractEntity{
    @Basic
    @Column(nullable = false, unique = true)
    private String code;

    @Basic
    @Column(nullable = false)
    private String title;


    @Column(columnDefinition = "text")
    private String description;



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "subject")
    private List<Thread> threads = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "studiedSubjects", cascade = CascadeType.ALL)
    private List<User> students = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "taughtSubjects", cascade = CascadeType.ALL)
    private List<User> teachers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL)
    private List<InviteLink> links = new ArrayList<>();


    public void addStudent(User student){
        if(!students.contains(student)){
            students.add(student);
        }
    }

    public void addTeacher(User teacher){
        if(!teachers.contains(teacher)){
            teachers.add(teacher);
        }
    }
    public void removeStudent(User student){
        students.remove(student);
    }

    public void removeTeacher(User teacher){
        teachers.remove(teacher);
    }


    public void addThread(Thread thread){
        if(!threads.contains(thread)){
            threads.add(thread);
        }
    }

    public Subject(SubjectDTO subjectDTO) {
        this.code = subjectDTO.getCode();
        this.title = subjectDTO.getTitle();
        this.description = subjectDTO.getDescription();
    }



    public Subject(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

}
