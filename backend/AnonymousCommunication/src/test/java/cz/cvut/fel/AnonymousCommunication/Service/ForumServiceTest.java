package cz.cvut.fel.AnonymousCommunication.Service;

import cz.cvut.fel.AnonymousCommunication.Model.Post;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.Thread;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ForumServiceTest {

    @Autowired
    ForumService forumService;

    @Autowired
    SubjectService subjectService;

    @Test
    public void createNewThread_ThreadTitleUnique_threadIsCreated(){
        Subject subject = new Subject();
        Thread thread = new Thread();
        thread.setTitle("New Thread");
        forumService.createThread(subject,thread);
        Assertions.assertEquals(1,subject.getThreads().size());
    }

    @Test
    public void createNewThread_threadTitleNotUnique_threadNotCreated(){
        Subject subject = new Subject();
        Thread thread = new Thread();
        thread.setTitle("New Thread");
        Thread thread2 = new Thread();
        thread2.setTitle("New Thread");


        forumService.createThread(subject,thread);
        Assertions.assertEquals(1,subject.getThreads().size());

        forumService.createThread(subject,thread2);
        Assertions.assertEquals(1,subject.getThreads().size());
    }

    @Test
    public void createPost_postCreated(){
        Subject subject = new Subject();
        Thread thread = new Thread();
        thread.setTitle("New Thread");

        forumService.createThread(subject,thread);

        Post post = new Post();

        forumService.createPost(thread,post);
        Assertions.assertEquals(1,thread.getPosts().size());
    }

    @Test
    public void editPost_postEdited(){
        Subject subject = new Subject();
        Thread thread = new Thread();
        thread.setTitle("New Thread");

        forumService.createThread(subject,thread);

        Post post = new Post();
        post.setContent("Content");

        forumService.createPost(thread,post);
        Assertions.assertEquals("Content",post.getContent());

        forumService.editPost(post,"New Content");
        post = forumService.findPostById(post.getId());
        Assertions.assertEquals("New Content",post.getContent());
    }
}
