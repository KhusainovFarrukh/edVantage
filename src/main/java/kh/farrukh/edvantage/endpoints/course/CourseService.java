package kh.farrukh.edvantage.endpoints.course;

import java.util.List;

public interface CourseService {

    List<Course> getCourses();

    Course getCourseById(long id);

    Course addCourse(CourseDTO courseDTO);

    Course updateCourse(long id, CourseDTO courseDTO);

    void deleteCourseById(long id);
}
