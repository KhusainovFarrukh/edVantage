package kh.farrukh.edvantage.endpoints.lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> getLessonsOfCourse(long courseId);

    Lesson getLessonById(long courseId, long id);

    Lesson addLesson(long courseId, LessonDTO lessonDTO);

    Lesson updateLesson(long courseId, long id, LessonDTO lessonDTO);

    void deleteLessonById(long courseId, long id);
}
