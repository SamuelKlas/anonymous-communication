package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstName(String firstName);
    User findByUserName(String userName);
    User findByDisplayName(String displayName);
    List<User> findAll();
}
