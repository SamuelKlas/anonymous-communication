package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Role;
import cz.cvut.fel.AnonymousCommunication.Repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@Data
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRep;

    public Optional<Role> findById(Long id){
        return roleRep.findById(id);
    }

    public List<Role> findAll(){
        return roleRep.findAll();
    }

    public Role findByName(String name){
        return roleRep.findByName(name);
    }
}
