package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractEntity{

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;

    @JsonIgnore
    @ManyToOne
    private Post repliesTo;

    @OneToMany(mappedBy = "repliesTo",cascade = CascadeType.ALL)
    private List<Post> replies = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    private Thread thread;

    @JsonIgnore
    @ManyToOne
    private User poster;

    private String posterDisplayName;

    private long userId;

    private long replyToId;

    public Post(Thread thread, String content, Post repliesTo,User poster) {
        this.thread = thread;
        this.timeCreated = new Date();
        this.content = content;
        this.repliesTo = repliesTo;
        this.poster = poster;
        this.posterDisplayName = poster.getDisplayName();
        this.userId = poster.getId();
        this.replyToId = repliesTo.getId();

    }

    public Post(Thread thread, String content,User poster) {
        this.thread = thread;
        this.timeCreated = new Date();
        this.content = content;
        this.poster = poster;
        //this.userId = poster.getId();
        this.posterDisplayName = poster.getDisplayName();
    }

    public void addReply(Post post){
        if(!replies.contains(post)) {
            replies.add(post);
        }
    }
}
