package kh.farrukh.edvantage.endpoints.course;

import kh.farrukh.edvantage.base.entity.EntityWithId;
import kh.farrukh.edvantage.endpoints.lesson.Lesson;
import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<String> tags = new ArrayList<>();
    @ManyToMany
    private List<AppUser> teachers = new ArrayList<>();
    // TODO: 8/5/22 needs endpoint for joining course
    @ManyToMany
    private List<AppUser> students = new ArrayList<>();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    public Course(CourseDTO courseDTO, UserRepository userRepository) {
        BeanUtils.copyProperties(courseDTO, this);
        courseDTO.getTeachers().forEach(teacherId -> {
            AppUser teacher = userRepository.findById(teacherId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", teacherId)
            );
            this.teachers.add(teacher);
        });
    }

    public Course(String title, Double price) {
        this.title = title;
        this.price = price;
    }
}
