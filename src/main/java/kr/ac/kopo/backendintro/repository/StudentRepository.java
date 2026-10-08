package kr.ac.kopo.backendintro.repository;

import kr.ac.kopo.backendintro.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContaining(
            String name, Sort sort); // ★ Sort 추가

    List<Student> findByMajorContaining(
            String major, Sort sort); // ★ Sort 추가

    List<Student> findByNameContainingAndMajorContaining(
            String name, String major, Sort sort); // ★ Sort 추가

    Page<Student> findByNameContaining(
            String name, Pageable pageable);

    Page<Student> findByMajorContaining(
            String major, Pageable pageable);

    Page<Student> findByNameContainingAndMajorContaining(
            String name, String major, Pageable pageable);
}



