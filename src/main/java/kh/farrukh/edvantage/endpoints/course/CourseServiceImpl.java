package kh.farrukh.edvantage.endpoints.course;

import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.utils.pagination.PagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public PagedList<Course> getCourses(int pageNumber, int pageSize) {
        Page<Course> page = courseRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        return new PagedList<>(page);
    }

    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", id)
        );
    }

    @Override
    public Course addCourse(CourseDTO courseDTO) {
        return courseRepository.save(new Course(courseDTO, userRepository));
    }

    @Override
    public Course updateCourse(long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", id)
        );
        List<AppUser> teachers = new java.util.ArrayList<>();
        courseDTO.getTeachers().forEach(teacherId -> {
            AppUser teacher = userRepository.findById(teacherId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", teacherId)
            );
            teachers.add(teacher);
        });
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setPrice(courseDTO.getPrice());
        existingCourse.setTags(courseDTO.getTags());
        existingCourse.setTeachers(teachers);
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
