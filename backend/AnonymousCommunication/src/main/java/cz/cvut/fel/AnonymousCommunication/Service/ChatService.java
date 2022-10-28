package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Chat;
import cz.cvut.fel.AnonymousCommunication.Model.Message;
import cz.cvut.fel.AnonymousCommunication.Repository.ChatRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;

    public Chat findById(long id){
        return chatRepository.findById(id).get();
    }

    public Chat save(Chat chat){
        return chatRepository.save(chat);
    }

    public List<Chat> findAll(){
        return chatRepository.findAll();
    }

    public void addMessage(Chat chat, Message message){
        messageRepository.save(message);
        chat.getMessages().add(message);
        chatRepository.save(chat);
    }
}
