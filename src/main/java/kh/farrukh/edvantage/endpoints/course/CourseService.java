package kh.farrukh.edvantage.endpoints.course;

import kh.farrukh.edvantage.utils.pagination.PagedList;

public interface CourseService {

    PagedList<Course> getCourses(int pageNumber, int pageSize);

    Course getCourseById(long id);

    Course addCourse(CourseDTO courseDTO);

    Course updateCourse(long id, CourseDTO courseDTO);

    void deleteCourseById(long id);
}
