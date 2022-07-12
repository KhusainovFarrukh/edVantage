package kh.farrukh.edvantage.endpoints.lesson;

import kh.farrukh.edvantage.utils.pagination.PagedList;

public interface LessonService {

    PagedList<Lesson> getLessonsOfCourse(long courseId, int pageNumber, int pageSize);

    Lesson getLessonById(long courseId, long id);

    Lesson addLesson(long courseId, LessonDTO lessonDTO);

    Lesson updateLesson(long courseId, long id, LessonDTO lessonDTO);

    void deleteLessonById(long courseId, long id);
}
