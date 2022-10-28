package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.Model.Chat;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Service.ChatService;
import cz.cvut.fel.AnonymousCommunication.Service.MessageService;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ChatController {
    private MessageService messageService;
    private ChatService chatService;
    private SubjectService subjectService;

    @PostMapping("/subjects/{subjectId}/chat")
    public ResponseEntity createChat(@PathVariable long subjectId){
        Subject subject = subjectService.findById(subjectId).get();
        Chat chat = new Chat();
        chatService.save(chat);
        System.out.println(subjectId);
        subjectService.addChat(subject,chat);
        System.out.println("created chat with id : " + chat.getId());
        System.out.println(chatService.findAll());
        return ResponseEntity.ok().body(chat);
    }

    @GetMapping("/subjects/{subjectId}/chat")
    public ResponseEntity getChats(@PathVariable long subjectId){
        Subject subject = subjectService.findById(subjectId).get();
        return ResponseEntity.ok().body(subject.getChats());
    }

    @GetMapping("/chat/{chatId}/messages")
    public ResponseEntity getMessages(@PathVariable long chatId){
        Chat chat = chatService.findById(chatId);
        return ResponseEntity.ok().body(chat.getMessages());
    }
}
