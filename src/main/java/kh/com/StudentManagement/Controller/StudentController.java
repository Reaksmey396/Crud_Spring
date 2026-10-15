package kh.com.StudentManagement.Controller;

import kh.com.StudentManagement.entity.Student;
import kh.com.StudentManagement.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student) {

        student.setId(null);

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    // READ ALL
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable Long id) {

        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student details) {

        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(details.getName());
                    student.setEmail(details.getEmail());
                    student.setCourse(details.getCourse());

                    Student updated =
                            studentRepository.save(student);

                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}