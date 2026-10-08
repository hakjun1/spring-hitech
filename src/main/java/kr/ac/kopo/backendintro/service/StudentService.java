package kr.ac.kopo.backendintro.service;

import kr.ac.kopo.backendintro.model.Student;
import kr.ac.kopo.backendintro.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElse(null);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public Student update(Long id, Student updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElse(null);
        if (student == null) {
            return null;
        }
        student.setName(updatedStudent.getName());
        student.setMajor(updatedStudent.getMajor());
        student.setGrade(updatedStudent.getGrade());
        return student;
    }


    public Page<Student> search(String nameKeyword, // ★ 반환 타입 Page 로 변경
                                String majorKeyword,
                                String sortField,
                                String sortDir,
                                int page, // ★ 추가
                                int size) { // ★ 추가
        boolean nameEmpty =
                nameKeyword == null || nameKeyword.isBlank();
        boolean majorEmpty =
                majorKeyword == null || majorKeyword.isBlank();
        String field;
        if ("name".equals(sortField)) {
            field = "name";
        } else if ("major".equals(sortField)) {
            field = "major";
        } else if ("grade".equals(sortField)) {
            field = "grade";
        } else {
            field = "id";
        }
        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, field);
        Pageable pageable = PageRequest.of(page, size, sort); // ★ 추가
        if (nameEmpty && majorEmpty) {
            return studentRepository.findAll(pageable); // ★ pageable 로 변경
        }
        if (!nameEmpty && majorEmpty) {
            return studentRepository.findByNameContaining(
                    nameKeyword, pageable); // ★ pageable 로 변경
        }
        if (nameEmpty) {
            return studentRepository.findByMajorContaining(
                    majorKeyword, pageable); // ★ pageable 로 변경
        }
        return studentRepository
                .findByNameContainingAndMajorContaining(
                        nameKeyword, majorKeyword, pageable); // ★ pageable 로 변경
    }

}
