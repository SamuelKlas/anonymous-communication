package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thread extends AbstractEntity{
    @Basic
    @Column(nullable = false , unique = true)
    private String title;

    @JsonIgnore
    @ManyToOne
    private User poster;

    @JsonIgnore
    @ManyToOne
    private Subject subject;

    private String posterDisplayName;


    @OneToMany(mappedBy = "thread",cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public Thread(String title, User poster) {
        this.title = title;
        this.poster = poster;
        this.posterDisplayName = poster.getDisplayName();
    }


}
