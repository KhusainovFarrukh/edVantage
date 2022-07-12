package kh.farrukh.edvantage.endpoints.lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> getLessons();

    Lesson getLessonById(long id);

    Lesson addLesson(LessonDTO lessonDTO);

    Lesson updateLesson(long id, LessonDTO lessonDTO);

    void deleteLessonById(long id);
}
