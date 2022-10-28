package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Post;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.Thread;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Repository.PostRepository;
import cz.cvut.fel.AnonymousCommunication.Repository.ThreadRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@Data
@AllArgsConstructor
public class ForumService {
    private ThreadRepository thRep;
    private PostRepository postRep;


    public Thread createThread(Subject subject, Thread thread){
//        subject.addThread(thread);
        thread.setSubject(subject);
        thRep.save(thread);
        return thread;
    }

    public Post createPost(Thread thread,Post post){
        thread.getPosts().add(post);
        postRep.save(post);
        return post;
    }

    public Thread findThreadById(long id){
        return thRep.findById(id).get();
    }

    public Post findPostById(long id){
        return postRep.findById(id).get();
    }


    public Post editPost(Post post,String newContent){
        post.setContent(newContent);
        return postRep.save(post);
    }

    public void addPostToUser(User user,Post post){
        user.getPosts().add(post);
        post.setPoster(user);

    }

    public void deletePost(Post post){
        postRep.delete(post);
    }



}
