package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.Thread;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
