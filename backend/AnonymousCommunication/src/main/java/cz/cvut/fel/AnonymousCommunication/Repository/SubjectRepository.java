package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    public Subject findSubjectByCode(String code);

}
