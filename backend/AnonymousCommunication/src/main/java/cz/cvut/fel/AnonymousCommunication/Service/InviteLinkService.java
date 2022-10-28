package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.InviteLink;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.InviteLinkRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.SubjectRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Data
@EnableScheduling
public class InviteLinkService {
    private SubjectRepository subjectRepository;
    private InviteLinkRepository inviteLinkRepository;

    @Autowired
    public InviteLinkService(SubjectRepository subjectRepository, InviteLinkRepository inviteLinkRepository) {
        this.subjectRepository = subjectRepository;
        this.inviteLinkRepository = inviteLinkRepository;
    }

    public InviteLink findById(long id){
        return inviteLinkRepository.findById(id).get();
    }

    public InviteLink findByUuid(String uuid){
        return inviteLinkRepository.findInviteLinkByUuid(uuid);
    }
    public void delete(InviteLink inviteLink){
        inviteLinkRepository.delete(inviteLink);
    }

    public InviteLink save(InviteLink inviteLink){
        return inviteLinkRepository.save(inviteLink);
    }

    public void addInviteLinkToSubject(Subject subject, InviteLink inviteLink){
        subject.getLinks().add(inviteLink);
        inviteLink.setSubject(subject);
        subjectRepository.save(subject);
    }

    /*schedule for midnight every day*/
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredInviteLinks() {
        Date now = new Date();
        List<InviteLink> links = inviteLinkRepository.findAll();
        for (int i = 0; i < links.size(); i++) {
            InviteLink link = links.get(i);
            if(link.getExpiringAt().after(now)){
                inviteLinkRepository.delete(link);
            }
        }
    }
}
