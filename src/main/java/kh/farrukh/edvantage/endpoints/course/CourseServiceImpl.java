package kh.farrukh.edvantage.endpoints.course;

import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", id)
        );
    }

    @Override
    public Course addCourse(CourseDTO courseDTO) {
        return courseRepository.save(new Course(courseDTO));
    }

    @Override
    public Course updateCourse(long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", id)
        );
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setPrice(courseDTO.getPrice());
        existingCourse.setTags(courseDTO.getTags());
        existingCourse.setTeachers(courseDTO.getTeachers());
        existingCourse.setStudents(courseDTO.getStudents());
        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourseById(long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course", "id", id);
        }
        courseRepository.deleteById(id);
    }
}
