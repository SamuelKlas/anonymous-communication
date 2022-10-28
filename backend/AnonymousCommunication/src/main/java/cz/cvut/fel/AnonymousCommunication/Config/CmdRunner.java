package cz.cvut.fel.AnonymousCommunication.Config;

import cz.cvut.fel.AnonymousCommunication.Model.*;
import cz.cvut.fel.AnonymousCommunication.Model.Thread;
import cz.cvut.fel.AnonymousCommunication.Repository.UserRepository;
import cz.cvut.fel.AnonymousCommunication.Service.ChatService;
import cz.cvut.fel.AnonymousCommunication.Service.ForumService;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import cz.cvut.fel.AnonymousCommunication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component @Transactional
public class CmdRunner implements CommandLineRunner {

    @Autowired
    private UserService uSer;
    @Autowired
    private SubjectService subSer;
    @Autowired
    private ForumService forSer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository uRep;
    @Autowired
    private ChatService chatService;
    @Profile("!test")
    @Override
    public void run(String... args) throws Exception {
        System.out.println("satgasasdgadgasdgasdgd");

        Role student = new Role("ROLE_STUDENT");
        Role teacher = new Role("ROLE_TEACHER");
        Role admin = new Role("ROLE_ADMIN");
        uSer.saveRole(student);
        uSer.saveRole(teacher);
        uSer.saveRole(admin);


        User u = new User("123",passwordEncoder.encode("123"));
        u.getRoles().add(student);
        uSer.saveRole(student);
        uSer.setRandomDisplayName(u);
        uSer.saveUser(u);
        System.out.println(u);

        uSer.saveRole(admin);
        User u22 = new User("456",passwordEncoder.encode("456"));
        u22.getRoles().add(student);
        uSer.setRandomDisplayName(u22);
        uSer.saveUser(u22);
        User u2 = new User("yeet",passwordEncoder.encode("yeet"));
        u2.setDisplayName("John Johnson");
        User u3 = new User("789",passwordEncoder.encode("457"));
        u3.setDisplayName("Mark Smith");
        User u4 = new User("78910",passwordEncoder.encode("458"));
        u4.setDisplayName("Camille Shaffer");
        uSer.saveRole(teacher);
        uSer.saveUser(u2);
        uSer.saveUser(u3);
        uSer.saveUser(u4);
        u2.getRoles().add(teacher);
        u3.getRoles().add(teacher);
        u4.getRoles().add(teacher);
        for (User user : uSer.findAll()) {
            System.out.println(user.getId());
        }
        for (int i = 0; i < 3; i++) {
            Subject subject = new Subject("B202PLHD" + i,"Placeholder subject "+ i,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
               subSer.saveSubject(subject);
               subSer.addStudentToSubject(u,subject);
               subSer.addStudentToSubject(u22,subject);
               subSer.addTeacherToSubject(u2,subject);
               subSer.addTeacherToSubject(u3,subject);
               subSer.addTeacherToSubject(u4,subject);
               Thread thread = new Thread("Placeholder question " + i,u);
               Thread thread2 = new Thread("Placeholder question 2 " + i,u);
               forSer.createThread(subject, thread);
               forSer.createThread(subject, thread2);

            System.out.println(u.getId());
            System.out.println(u2.getId());
            Post a1 = new Post(thread,"First question in thread",u);
            forSer.createPost(thread,a1);
            Post a2 = new Post(thread,"Response to first question",a1,u2);
            forSer.createPost(thread,a2);
            Post a3 = new Post(thread,"Second nested response",a2,u2);
            forSer.createPost(thread,a3);

            Post a21 = new Post(thread,"Second response to first question",a1,u2);
            forSer.createPost(thread,a21);

            Post b1 = new Post(thread,"Second question in thread",u);
            forSer.createPost(thread,b1);
            Post b2 = new Post(thread,"Response to second question",b1,u);
            forSer.createPost(thread,b2 );

        }

    }


}
