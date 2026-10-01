package kr.ac.kopo.backendintro.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Student {
    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "학과를 입력하세요.")
    private String major;

    @Min(value = 1, message = "학년은 1 이상이어야 합니다.")
    @Max(value = 2, message = "학년은 2 이하여야 합니다.")
    private int grade;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
