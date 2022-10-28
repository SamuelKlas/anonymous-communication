package cz.cvut.fel.AnonymousCommunication.Controller;

import cz.cvut.fel.AnonymousCommunication.DTO.SubjectDTO;
import cz.cvut.fel.AnonymousCommunication.DTO.SubjectGetDTO;
import cz.cvut.fel.AnonymousCommunication.Model.Subject;
import cz.cvut.fel.AnonymousCommunication.Model.User;
import cz.cvut.fel.AnonymousCommunication.Service.SubjectService;
import cz.cvut.fel.AnonymousCommunication.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@Data
@AllArgsConstructor
public class SubjectController {
    private SubjectService subSer;
    private UserService uSer;


    @GetMapping("")
    public ResponseEntity<List<Subject>> findAll(){
        return ResponseEntity.ok().body(subSer.findAll());
    }

    @PostMapping("")
    public ResponseEntity saveSubject(@RequestBody SubjectDTO subjectDTO){

        Subject subject = subSer.saveSubject(new Subject(subjectDTO));
        if(subject != null){
            return ResponseEntity.ok().body(subject);
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Subject with that code already exists");
        }

    }

    @GetMapping("/{subjectId}")
    public ResponseEntity getSubject(@PathVariable long subjectId){
        Subject subject = subSer.findById(subjectId).get();
        SubjectGetDTO subjectGetDTO = new SubjectGetDTO(subject);
        return ResponseEntity.ok().body(subjectGetDTO);
    }

    @PutMapping("/{subjectId}/student/{studentId}")
    public ResponseEntity addStudentToSubject(@PathVariable long subjectId, @PathVariable long studentId){
        User student =  uSer.findById(studentId).get();
        Subject subject = subSer.findById(subjectId).get();
        subSer.addStudentToSubject(student,subject);

        return ResponseEntity.ok().body(subject);
    }

    @PutMapping("/{subjectId}/teacher/{teacherId}")
    public ResponseEntity addTeacherToSubject(@PathVariable long subjectId, @PathVariable long teacherId){
        User teacher =  uSer.findById(teacherId).get();
        Subject subject = subSer.findById(subjectId).get();
        subSer.addTeacherToSubject(teacher,subject);

        return ResponseEntity.ok().body(subject);
    }

    @DeleteMapping("/{subjectId}/student/{studentId}")
    public ResponseEntity removeStudentFromSubject(@PathVariable long subjectId, @PathVariable long studentId){
        User student =  uSer.findById(studentId).get();
        Subject subject = subSer.findById(subjectId).get();
        subSer.removeStudentFromSubject(student,subject);

        return ResponseEntity.ok().body(subject);
    }

    @DeleteMapping("/{subjectId}/teacher/{teacherId}")
    public ResponseEntity removeTeacherFromSubject(@PathVariable long subjectId, @PathVariable long teacherId){
        User teacher =  uSer.findById(teacherId).get();
        Subject subject = subSer.findById(subjectId).get();
        subSer.removeTeacherFromSubject(teacher,subject);

        return ResponseEntity.ok().body(subject);
    }



    @GetMapping("/{subjectId}/students")
    public ResponseEntity getStudentsForSubject(@PathVariable long subjectId){
        Subject subject = subSer.findById(subjectId).get();
        return ResponseEntity.ok().body(subject.getStudents());
    }

    @GetMapping("/{subjectId}/teachers")
    public ResponseEntity getTeachersForSubject(@PathVariable long subjectId){
        Subject subject = subSer.findById(subjectId).get();
        return ResponseEntity.ok().body(subject.getTeachers());
    }

}
