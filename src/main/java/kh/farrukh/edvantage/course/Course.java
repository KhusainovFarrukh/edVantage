package kh.farrukh.edvantage.course;

import kh.farrukh.edvantage.base.entity.EntityWithId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends EntityWithId {

    @Column(nullable = false)
    private String name;
    private Double price;
    @ElementCollection
    private List<String> teachers;
    @ElementCollection
    private List<String> students;
    @ElementCollection
    private List<String> tags;
    @ElementCollection
    private List<String> lessons;

    public Course(CourseDTO courseDTO) {
        BeanUtils.copyProperties(courseDTO, this);
    }
}
