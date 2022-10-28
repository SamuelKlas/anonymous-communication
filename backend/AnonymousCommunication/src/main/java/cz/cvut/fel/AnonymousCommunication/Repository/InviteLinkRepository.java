package cz.cvut.fel.AnonymousCommunication.Repository;

import cz.cvut.fel.AnonymousCommunication.Model.InviteLink;
import cz.cvut.fel.AnonymousCommunication.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteLinkRepository extends JpaRepository<InviteLink,Long> {
    public InviteLink findInviteLinkByUuid(String uuid);
}
