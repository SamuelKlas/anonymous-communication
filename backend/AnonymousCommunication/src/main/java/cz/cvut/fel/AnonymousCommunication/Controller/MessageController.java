package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.DTO.MessageDTO;
import cz.cvut.fel.AnonymousCommunication.Model.Chat;
import cz.cvut.fel.AnonymousCommunication.Model.Message;
import cz.cvut.fel.AnonymousCommunication.Service.ChatService;
import cz.cvut.fel.AnonymousCommunication.Service.MessageService;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor

@RestController
public class MessageController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageService messageService;
    private ChatService chatService;
    private SubjectService subjectService;

    @MessageMapping("/subject/{subjectId}/chat/{chatId}")
    public void send(@DestinationVariable long subjectId, @DestinationVariable long chatId, @Payload MessageDTO messageDTO) {
        Chat chat = chatService.findById(chatId);
        chatService.addMessage(chat,new Message(messageDTO));
        System.out.println(chat.getMessages());
        System.out.println("adding to chat with id : " + chat.getId());
        System.out.println(chatService.findAll());
        simpMessagingTemplate.convertAndSend("/topic/subject/"+ subjectId + "/chat/" + chatId,messageDTO    );
    }

//    @MessageMapping("/subject")
//    public void send(@Payload MessageDTO messageDTO) {
////        Chat chat = chatService.findById(chatId);
////        chatService.addMessage(chat,new Message(messageDTO));
////        System.out.println(messageDTO.getContent());
//        simpMessagingTemplate.convertAndSend("/topic/subject",messageDTO.getContent());
//    }
}
