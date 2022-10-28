package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.QuizEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizEntryRepository extends JpaRepository<QuizEntry,Long> {
}
