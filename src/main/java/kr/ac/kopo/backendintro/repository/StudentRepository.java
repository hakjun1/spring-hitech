package kr.ac.kopo.backendintro.repository;

import kr.ac.kopo.backendintro.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
