package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.Model.Role;
import cz.cvut.fel.AnonymousCommunication.Service.RoleService;
import cz.cvut.fel.AnonymousCommunication.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Data @AllArgsConstructor
public class RoleController {
    private UserService uSer;
    private RoleService roleSer;


    @GetMapping("")
    public ResponseEntity<List<Role>> findAll(){
        return ResponseEntity.ok().body(roleSer.findAll());
    }
}
