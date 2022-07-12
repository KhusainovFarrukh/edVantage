package kh.farrukh.edvantage.utils.checkers;

import kh.farrukh.edvantage.endpoints.course.CourseRepository;
import kh.farrukh.edvantage.endpoints.lesson.LessonRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;

public class CheckUtils {

    public static void checkCourseId(long courseId, CourseRepository courseRepository) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
    }

    public static void checkLessonId(long lessonId, LessonRepository lessonRepository) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson", "id", lessonId);
        }
    }
}
