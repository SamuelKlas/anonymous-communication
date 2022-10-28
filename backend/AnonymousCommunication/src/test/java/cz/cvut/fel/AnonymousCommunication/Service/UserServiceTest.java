package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.SubjectRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
public class UserServiceTest {

    @MockBean
    SubjectRepository subjectRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    SubjectService subjectService;

    public User generateUser(){
        return new User("123","123");
    }

    @Test
    public void resetStudentDisplayName_resetAvailable_displayNameIsReset(){
        User user = generateUser();
        String oldDisplayName = "Student123";
        user.setDisplayName(oldDisplayName);

        userService.setRandomDisplayName(user);
        Assertions.assertNotEquals(oldDisplayName,user.getDisplayName());

    }

    @Test
    public void resetStudentDisplayName_resetNotAvailable_displayNameNotReset(){
        User user = generateUser();
        String oldDisplayName = "Student123";
        user.setDisplayName(oldDisplayName);
        user.setCanResetDisplayName(false);


        userService.setRandomDisplayName(user);
        Assertions.assertEquals(oldDisplayName,user.getDisplayName());

    }
}
