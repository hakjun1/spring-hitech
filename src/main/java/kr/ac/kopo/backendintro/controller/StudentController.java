package kr.ac.kopo.backendintro.controller;

import jakarta.validation.Valid;
import kr.ac.kopo.backendintro.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    @GetMapping("/student-form")
    public String studentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/student")
    public String student(
            @Valid @ModelAttribute Student student,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "student-form";
        }
        model.addAttribute("student", student);
        return "student";
    }
}
