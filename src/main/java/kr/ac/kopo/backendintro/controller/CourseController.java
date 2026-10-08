package kr.ac.kopo.backendintro.controller;

import jakarta.validation.Valid;
import kr.ac.kopo.backendintro.model.Course;
import kr.ac.kopo.backendintro.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/course-form")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course-form";
    }

    @PostMapping("/course")
    public String course(
            @Valid @ModelAttribute Course course,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course-form";
        }
        courseService.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses")
    public String courses(
            @RequestParam(required = false) String nameKeyword,
            @RequestParam(required = false) String teacherKeyword,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<Course> coursePage = courseService.search(
                nameKeyword, teacherKeyword,
                sortField, sortDir, page, size);
        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", coursePage.getNumber());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("totalItems", coursePage.getTotalElements());
        model.addAttribute("nameKeyword", nameKeyword);
        model.addAttribute("teacherKeyword", teacherKeyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("size", size);
        return "courses";
    }

    @GetMapping("/courses/{id}")
    public String courseDetail(@PathVariable Long id,
                               Model model) {
        Course course = courseService.findById(id);
        if (course == null) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        return "course-detail";
    }

    @GetMapping("/courses/{id}/edit")
    public String courseEditForm(@PathVariable Long id,
                                 Model model) {
        Course course = courseService.findById(id);
        if (course == null) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        return "course-edit";
    }

    @PostMapping("/courses/{id}/edit")
    public String courseEdit(
            @PathVariable Long id,
            @Valid @ModelAttribute Course course,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            course.setId(id);
            return "course-edit";
        }
        courseService.update(id, course);
        return "redirect:/courses";
    }

    @PostMapping("/courses/{id}/delete")
    public String courseDelete(@PathVariable Long id) {
        courseService.deleteById(id);
        return "redirect:/courses";
    }
}
