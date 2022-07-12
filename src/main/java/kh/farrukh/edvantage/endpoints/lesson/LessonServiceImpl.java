package kh.farrukh.edvantage.endpoints.lesson;

import kh.farrukh.edvantage.endpoints.course.CourseRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.utils.checkers.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Lesson> getLessonsOfCourse(long courseId) {
        CheckUtils.checkCourseId(courseId, courseRepository);
        return lessonRepository.findByCourse_Id(courseId);
    }

    @Override
    public Lesson getLessonById(long courseId, long id) {
        CheckUtils.checkCourseId(courseId, courseRepository);
        return lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lesson", "id", id)
        );
    }

    @Override
    public Lesson addLesson(long courseId, LessonDTO lessonDTO) {
        Lesson lesson = new Lesson(lessonDTO);
        lesson.setCourseById(courseId, courseRepository);
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson updateLesson(long courseId, long id, LessonDTO lessonDTO) {
        Lesson existingLesson = lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lesson", "id", id)
        );
        existingLesson.setTitle(lessonDTO.getTitle());
        existingLesson.setAuthor(lessonDTO.getAuthor());
        existingLesson.setTextBody(lessonDTO.getTextBody());
        existingLesson.setCourseById(courseId, courseRepository);
        return lessonRepository.save(existingLesson);
    }

    @Override
    public void deleteLessonById(long courseId, long id) {
        CheckUtils.checkCourseId(courseId, courseRepository);
        CheckUtils.checkLessonId(id, lessonRepository);
        lessonRepository.deleteById(id);
    }
}
