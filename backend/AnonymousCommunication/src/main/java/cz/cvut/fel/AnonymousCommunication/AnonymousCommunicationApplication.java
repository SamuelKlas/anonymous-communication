package cz.cvut.fel.AnonymousCommunication;

import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RestController
public class AnonymousCommunicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnonymousCommunicationApplication.class, args);
	}

	@GetMapping("")
	public String hello(){
		return "hello";
	}

	@GetMapping("/secret")
	public String index(){
		return "login successful!";
	}

}
