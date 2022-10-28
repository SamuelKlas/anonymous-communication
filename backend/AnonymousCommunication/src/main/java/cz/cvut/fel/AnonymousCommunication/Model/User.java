package cz.cvut.fel.AnonymousCommunication.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.cvut.fel.AnonymousCommunication.DTO.UserRegisterDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {

    @Basic
    @Column(unique = true)
    private String userName;

    @Basic
    private String firstName;

    @Basic
    private String lastName;

    @Basic
    private String password;

    @Column
    private boolean canResetDisplayName;

    @Basic
    @Column(unique = true)
    private String displayName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<Subject> taughtSubjects = new ArrayList<>();


    @JsonIgnore
    @ManyToMany
    private List<Subject> studiedSubjects = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "poster")
    private List<Thread> threads = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "poster")
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        post.setPoster(this);
        posts.add(post);
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public void addRole(Role role){
        if(!roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(Role role){
        if(roles.contains(role)){
            roles.remove(role);
        }
    }

    public void addStudiedSubject(Subject subject){
        if(!studiedSubjects.contains(subject)){
            studiedSubjects.add(subject);
        }
    }
    public void addTaughtSubject(Subject subject){
        if(!taughtSubjects.contains(subject)){
            taughtSubjects.add(subject);
        }
    }

    public void removeStudiedSubject(Subject subject){
        studiedSubjects.remove(subject);
    }

    public void removeTaughtSubject(Subject subject){
        taughtSubjects.remove(subject);
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public boolean isPoster(Post post){
        return this.posts.contains(post);
    }

    public boolean isPoster(Thread thread){
        return this.threads.contains(thread);
    }

    public boolean isInSubject(Subject subject){
        return this.studiedSubjects.contains(subject)
                || this.taughtSubjects.contains(subject);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.canResetDisplayName = true;
    }

    public User(UserRegisterDTO userRegisterDTO){
        this.userName = userRegisterDTO.getUsername();
        this.firstName = userRegisterDTO.getFirstName();
        this.lastName = userRegisterDTO.getLastName();
        this.password = userRegisterDTO.getPassword();
        this.canResetDisplayName = true;
    }
}
