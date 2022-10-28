package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

}
