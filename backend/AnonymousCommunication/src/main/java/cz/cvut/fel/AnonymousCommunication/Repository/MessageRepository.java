package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.Message;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
