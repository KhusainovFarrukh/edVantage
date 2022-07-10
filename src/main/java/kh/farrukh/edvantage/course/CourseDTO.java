package kh.farrukh.edvantage.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

@Data
public class CourseDTO {
    @NotBlank
    private String name;
    private Double price = 0.0;
    private List<String> teachers = Collections.emptyList();
    private List<String> students = Collections.emptyList();
    private List<String> tags = Collections.emptyList();
    private List<String> lessons = Collections.emptyList();
}
