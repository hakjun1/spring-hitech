package kr.ac.kopo.backendintro.repository;

import kr.ac.kopo.backendintro.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * findByTeacherContaining
 * findByNameContaining
 */
public interface CourseRepository extends JpaRepository<Course, Long> {


    Page<Course> findBySubjectContaining(String subject, Pageable pageable);

    Page<Course> findByTeacherContaining(String teacher, Pageable pageable);

    Page<Course> findBySubjectContainingAndTeacherContaining(String subject, String teacher, Pageable pageable);
}
