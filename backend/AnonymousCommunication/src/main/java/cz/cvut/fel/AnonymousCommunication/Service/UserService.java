package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.DTO.UserRegisterDTO;
import cz.cvut.fel.AnonymousCommunication.Model.Role;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.RoleRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.RoleRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.UserRepository;
import cz.cvut.fel.AnonymousCommunication.Utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service @Slf4j @Transactional
@AllArgsConstructor
@EnableScheduling
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private SecurityUtils utils;



    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails details = userRepository.findByUserName(userName);
        if (details == null){
            throw new UsernameNotFoundException("user does not exist");

        }
        return details;
    }

    public List<User> findAll(){
        log.info("Getting all users");
        return userRepository.findAll();

    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User getLoggedInUser(){
        String username = utils.getName();
        return (User) this.loadUserByUsername(username);
    }

    @Transactional
    public User saveUser(User user){
        boolean alreadyExists = this.exists(user);
        if(alreadyExists == false){
            log.info("Saving user {} to the database",user.getUsername());
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return user;
        }
        else{
            System.out.println("already exists");
            log.info("User with name {} already exists",user.getUsername());
            return null;
        }


    }

    public User registerUser(UserRegisterDTO userRegisterDTO){
        User user = new User(userRegisterDTO);
        return this.saveUser(user);
    }

    public Role saveRole(Role role){
        log.info("Saving role {} to the database",role.getName());
        return roleRepository.save(role);
    }


    public void addRoleToUser(Role role, User user){
        log.info("Adding role {} to user {}",role.getName(),user.getUsername());
        user.addRole(role);

    }

    public void removeRoleFromUser(Role role, User user){
        log.info("Removing role {} from user {}",role.getName(),user.getUsername());
        user.removeRole(role);

    }

    public boolean exists(User user){
        User found;
        try {
            found = (User) this.loadUserByUsername(user.getUsername());
        }catch(UsernameNotFoundException e){
            return false;
        }
        return found != null;

    }

    public void setRandomDisplayName(User user){
        String displayName = "Student"+ new Random().nextInt(1000);
        while(userRepository.findByDisplayName(displayName) != null){
            displayName = "Student"+ new Random().nextInt(1000);
        }
        user.setDisplayName(displayName);
        user.setCanResetDisplayName(false);
        System.out.println(user.getDisplayName());
        userRepository.save(user);
    }

    /*schedule for midnight every day*/
    @Scheduled(cron = "0 0 0 * * *")
    public void enableChangingOfDisplayName() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            user.setCanResetDisplayName(true);
            userRepository.save(user);
            System.out.println(user.isCanResetDisplayName());
        });
    }





}
