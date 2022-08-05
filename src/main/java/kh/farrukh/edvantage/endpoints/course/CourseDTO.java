package kh.farrukh.edvantage.endpoints.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    @NotBlank
    private String title;
    private Double price = 0.0;
    private List<String> tags = Collections.emptyList();
    private List<Long> teachers = Collections.emptyList();

    public CourseDTO(String title, Double price) {
        this.title = title;
        this.price = price;
    }
}
