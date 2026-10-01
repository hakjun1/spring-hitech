package kr.ac.kopo.backendintro.repository;

import kr.ac.kopo.backendintro.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    private final List<Student> students = new ArrayList<>();
    private long sequence = 0;

    public Student save(Student student) {
        student.setId(++sequence);
        students.add(student);
        return student;
    }

    public List<Student> findAll() {
        return students;
    }

    public Student findById(Long id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public Student update(Long id, Student updatedStudent) {
        Student student = findById(id);

        if (student == null) {
            return null;
        }
        student.setName(updatedStudent.getName());
        student.setMajor(updatedStudent.getMajor());
        student.setGrade(updatedStudent.getGrade());

        return student;
    }

    public boolean deleteById(Long id) {
        Student student = findById(id);
        if (student == null) {
            return false;
        }
        students.remove(student);
        return true;
    }
}
