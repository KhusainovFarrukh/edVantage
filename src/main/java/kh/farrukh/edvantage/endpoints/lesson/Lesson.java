package kh.farrukh.edvantage.endpoints.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import kh.farrukh.edvantage.base.entity.EntityWithId;
import kh.farrukh.edvantage.endpoints.course.Course;
import kh.farrukh.edvantage.endpoints.course.CourseRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends EntityWithId {

    @Column(nullable = false)
    private String title;
    private String author;
    @JsonProperty("text_body")
    @Column(name = "text_body")
    private String textBody;
    @ElementCollection
    private List<String> videos = Collections.emptyList();

    public Lesson(LessonDTO lessonDTO) {
        BeanUtils.copyProperties(lessonDTO, this);
    }

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public void setCourseById(long courseId, CourseRepository courseRepository) {
        this.course = courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseId)
        );
    }
}
