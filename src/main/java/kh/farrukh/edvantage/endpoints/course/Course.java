package kh.farrukh.edvantage.endpoints.course;

import kh.farrukh.edvantage.base.entity.EntityWithId;
import kh.farrukh.edvantage.endpoints.lesson.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends EntityWithId {

    @Column(nullable = false)
    private String title;
    private Double price;
    @ElementCollection
    private List<String> tags = Collections.emptyList();
    @ElementCollection
    private List<String> teachers = Collections.emptyList();
    @ElementCollection
    private List<String> students = Collections.emptyList();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons = Collections.emptyList();

    public Course(CourseDTO courseDTO) {
        BeanUtils.copyProperties(courseDTO, this);
    }

    public Course(String title, Double price) {
        this.title = title;
        this.price = price;
    }
}
