package kr.ac.kopo.backendintro.controller;

import jakarta.validation.Valid;
import kr.ac.kopo.backendintro.model.Student;
import kr.ac.kopo.backendintro.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student-form")
    public String studentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/student")
    public String student(
            @Valid @ModelAttribute Student student,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "student-form";
        }
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students";
    }

    @GetMapping("/students/{id}")
    public String studentDetail(@PathVariable Long id,
                                Model model) {
        Student student = studentRepository.findById(id)
                .orElse(null);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-detail";
    }

    @GetMapping("/students/{id}/edit")
    public String studentEditForm(@PathVariable Long id,
                                  Model model) {
        Student student = studentRepository.findById(id)
                .orElse(null);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-edit";
    }

    @PostMapping("/students/{id}/edit")
    public String studentEdit(@PathVariable Long id,
                              @Valid @ModelAttribute Student student,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            student.setId(id);
            return "student-edit";
        }
        student.setId(id);
        studentRepository.save(student);
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/delete")
    public String studentDelete(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }

}
