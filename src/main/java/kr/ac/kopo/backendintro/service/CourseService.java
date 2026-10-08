package kr.ac.kopo.backendintro.service;

import kr.ac.kopo.backendintro.model.Course;
import kr.ac.kopo.backendintro.model.Student;
import kr.ac.kopo.backendintro.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * findByTeacherContaining
 * findByNameContaining
 */
@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public Course update(Long id, Course updatedCourse) {
        Course course = courseRepository.findById(id)
                .orElse(null);
        if (course == null) {
            return null;
        }
        course.setSubject(updatedCourse.getSubject());
        course.setTeacher(updatedCourse.getTeacher());
        return course;
    }

    // 검색 + 정렬 + 페이징 통합 처리
    public Page<Course> search(String subjectKeyword,
                               String teacherKeyword,
                               String sortField,
                               String sortDir,
                               int page,
                               int size) {

        boolean subjectEmpty = (subjectKeyword == null || subjectKeyword.isBlank());
        boolean teacherEmpty = (teacherKeyword == null || teacherKeyword.isBlank());

        String field;
        if ("subject".equals(sortField) || "name".equals(sortField)) {
            field = "subject";
        } else if ("teacher".equals(sortField)) {
            field = "teacher";
        } else {
            field = "id";
        }

        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, field);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (subjectEmpty && teacherEmpty) {
            return courseRepository.findAll(pageable);
        }
        if (!subjectEmpty && teacherEmpty) {
            // 변경된 리포지토리 메서드 호출
            return courseRepository.findBySubjectContaining(subjectKeyword, pageable);
        }
        if (subjectEmpty) {
            return courseRepository.findByTeacherContaining(teacherKeyword, pageable);
        }
        // 변경된 리포지토리 메서드 호출
        return courseRepository.findBySubjectContainingAndTeacherContaining(
                subjectKeyword, teacherKeyword, pageable);
    }
}
