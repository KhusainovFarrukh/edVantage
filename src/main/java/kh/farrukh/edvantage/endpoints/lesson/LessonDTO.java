package kh.farrukh.edvantage.endpoints.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    @NotBlank
    private String title;
    private String author;
    @JsonProperty("text_body")
    private String textBody;
    private List<String> videos = Collections.emptyList();
    private long course;
}
