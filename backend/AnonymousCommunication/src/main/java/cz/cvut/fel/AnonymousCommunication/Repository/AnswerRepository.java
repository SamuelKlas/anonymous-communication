package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
