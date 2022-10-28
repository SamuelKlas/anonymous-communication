package cz.cvut.fel.AnonymousCommunication;

import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.SubjectRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.UserRepository;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import cz.cvut.fel.AnonymousCommunication.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AnonymousCommunicationApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertEquals(2,2);
	}



}
