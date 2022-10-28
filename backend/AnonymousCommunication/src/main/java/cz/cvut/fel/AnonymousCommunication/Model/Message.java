package cz.cvut.fel.AnonymousCommunication.Model;

import cz.cvut.fel.AnonymousCommunication.DTO.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractEntity{

    private String content;
    private String poster;

    public Message(MessageDTO messageDTO){
        this.content = messageDTO.getContent();
        this.poster = messageDTO.getPoster();
    }
}
