package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.DTO.UserDTO;
import cz.cvut.fel.AnonymousCommunication.DTO.UserRegisterDTO;
import cz.cvut.fel.AnonymousCommunication.Model.Role;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Service.RoleService;
import cz.cvut.fel.AnonymousCommunication.Service.UserService;
import cz.cvut.fel.AnonymousCommunication.Utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@Data @AllArgsConstructor
public class UserController {

    private SecurityUtils securityUtils;
    private UserService uSer;
    private RoleService roleSer;


    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(uSer.findAll());

    }

    @GetMapping("/profile")
    public ResponseEntity findProfile() {
        return ResponseEntity.ok().body(uSer.getLoggedInUser());

    }

    @PutMapping("/reset")
    public ResponseEntity resetDisplayName() {
        User user = uSer.getLoggedInUser();
        if(!user.isCanResetDisplayName()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
        }
        uSer.setRandomDisplayName(user);
        return ResponseEntity.ok().body(user);

    }


    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserRegisterDTO userRegisterDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").toUriString());
        User user = new User(userRegisterDTO);
        User saved = uSer.saveUser(user);
        if(saved != null){
            uSer.setRandomDisplayName(saved);
            uSer.addRoleToUser(roleSer.findByName("ROLE_STUDENT"),saved);
            return ResponseEntity.created(uri).body(saved);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).location(uri).body("Username already exists");
        }

    }

    @GetMapping("/users/{userId}/subjects")
    public ResponseEntity getSubjectsOfStudent(@PathVariable long userId){
        User user = uSer.findById(userId).get();
    
        return ResponseEntity.ok().body(user.getStudiedSubjects());
    }

    @PutMapping("/users/{userId}/role/{roleId}")
    public ResponseEntity addRoleToUser(@PathVariable long userId, @PathVariable long roleId){
        User user = uSer.findById(userId).get();
        Role role = roleSer.findById(roleId).get();
        uSer.addRoleToUser(role,user);

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/users/{userId}/role/{roleId}")
    public ResponseEntity removeRoleFromUser(@PathVariable long userId, @PathVariable long roleId){
        User user = uSer.findById(userId).get();
        Role role = roleSer.findById(roleId).get();
        uSer.removeRoleFromUser(role,user);

        return ResponseEntity.ok().body(user);
    }







}
