package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.DTO.InviteLinkDTO;
import cz.cvut.fel.AnonymousCommunication.Model.InviteLink;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Service.InviteLinkService;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import cz.cvut.fel.AnonymousCommunication.Service.UserService;
import cz.cvut.fel.AnonymousCommunication.Utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@AllArgsConstructor
public class InviteLinkController {
    private SubjectService subjectService;
    private InviteLinkService inviteLinkService;
    private UserService userService;


    @GetMapping("/subjects/{subjectId}/links")
    public ResponseEntity getLinks(@PathVariable long subjectId){
        Subject subject = subjectService.findById(subjectId).get();
        return ResponseEntity.ok().body(subject.getLinks());
    }

    @DeleteMapping("/links/{linkUuid}")
    public ResponseEntity getLinks(@PathVariable String linkUuid){
        InviteLink inviteLink = inviteLinkService.findByUuid(linkUuid);
        inviteLinkService.delete(inviteLink);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/subjects/{subjectId}/links")
    public ResponseEntity createInviteLink(@PathVariable long subjectId, @RequestBody InviteLinkDTO inviteLinkDTO){
        Subject subject = subjectService.findById(subjectId).get();
        InviteLink inviteLink = new InviteLink(subject,inviteLinkDTO.getExpiresSecondsFromNow());
        inviteLinkService.save(inviteLink);
        inviteLinkService.addInviteLinkToSubject(subject,inviteLink);
        System.out.println(subject.getLinks());
        return ResponseEntity.ok().body(inviteLink);
    }

    @PutMapping("/links/{linkUuid}")
    public ResponseEntity joinSubjectFromInviteLink(@PathVariable String linkUuid){
        InviteLink inviteLink = inviteLinkService.findByUuid(linkUuid);
        if(inviteLink == null){
            System.out.println("teeey");
        }

        if(inviteLink.getExpiringAt().toInstant().isBefore(Instant.now())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Invite link has expired");
        }

        User user = userService.getLoggedInUser();
        Subject subject = inviteLink.getSubject();
        subjectService.addStudentToSubject(user,subject);
        System.out.println("yeeeahahash");
        return ResponseEntity.ok().body(subject.getId());
    }
}
