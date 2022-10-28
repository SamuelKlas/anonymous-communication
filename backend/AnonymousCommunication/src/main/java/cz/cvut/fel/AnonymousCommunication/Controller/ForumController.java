package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.DTO.PostDTO;
import cz.cvut.fel.AnonymousCommunication.DTO.ThreadDTO;
import cz.cvut.fel.AnonymousCommunication.Model.*;
import cz.cvut.fel.AnonymousCommunication.Model.Thread;
import cz.cvut.fel.AnonymousCommunication.Service.ForumService;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import cz.cvut.fel.AnonymousCommunication.Service.UserService;
import cz.cvut.fel.AnonymousCommunication.Utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@Data
@AllArgsConstructor
public class ForumController {

    private SecurityUtils securityUtils;
    private SubjectService subSer;
    private ForumService forSer;
    private UserService uSer;

    @PostMapping("/{subjectId}/forum/threads")
    public ResponseEntity createThread(@PathVariable long subjectId,
                                       @RequestBody ThreadDTO threadDTO){
        User poster = uSer.getLoggedInUser();
        Thread thread = new Thread(threadDTO.getTitle(),poster);
        Subject subject = subSer.findById(subjectId).get();
        return ResponseEntity.ok().body(forSer.createThread(subject,thread));
    }

    @GetMapping("/{subjectId}/forum/threads")
    public ResponseEntity findThreads(@PathVariable long subjectId){
        Subject subject = subSer.findById(subjectId).get();
        return ResponseEntity.ok().body(subject.getThreads());
    }

    @GetMapping("/{subjectId}/forum/threads/{threadId}")
    public ResponseEntity findThread(@PathVariable long subjectId,
                                     @PathVariable long threadId){
        return ResponseEntity.ok().body(forSer.findThreadById(threadId));
    }

    @PostMapping("/{subjectId}/forum/threads/{threadId}")
    public ResponseEntity createPost(@PathVariable long subjectId,
                                     @PathVariable long threadId,
                                     @RequestBody PostDTO postDTO){

        Thread thread = forSer.findThreadById(threadId);
        Post created = new Post(thread,postDTO.getContent(),uSer.getLoggedInUser());
        User poster = uSer.getLoggedInUser();
        forSer.addPostToUser(poster,created);
        return ResponseEntity.ok().body(forSer.createPost(thread,created));

    }

    @PostMapping("/{subjectId}/forum/threads/{threadId}/{postId}")
    public ResponseEntity createPost(@PathVariable long subjectId,
                                     @PathVariable long threadId,
                                     @PathVariable long postId,
                                     @RequestBody PostDTO postDTO){

        Thread thread = forSer.findThreadById(threadId);
        Post repliesTo = forSer.findPostById(postId);
        Post created = new Post(thread,postDTO.getContent(),repliesTo,uSer.getLoggedInUser());
        User poster = uSer.getLoggedInUser();
        forSer.addPostToUser(poster,created);
        return ResponseEntity.ok().body(forSer.createPost(thread,created));

    }

    @GetMapping("/{subjectId}/forum/posts/{postId}")
    public ResponseEntity getPost(@PathVariable long subjectId,
                                  @PathVariable long postId){
        return ResponseEntity.ok().body(forSer.findPostById(postId));
    }

    @PutMapping("/{subjectId}/forum/posts/{postId}")
    public ResponseEntity editPost(@PathVariable long subjectId,
                                     @PathVariable long postId,
                                     @RequestBody PostDTO postDTO){

        Post post = forSer.findPostById(postId);
        return ResponseEntity.ok().body(
                forSer.editPost(post,postDTO.getContent()));

    }

    @DeleteMapping("/{subjectId}/forum/posts/{postId}")
    public ResponseEntity deletePost(@PathVariable long subjectId,
                                   @PathVariable long postId){

        Post post = forSer.findPostById(postId);
        forSer.deletePost(post);
        return ResponseEntity.noContent().build();

    }






}
