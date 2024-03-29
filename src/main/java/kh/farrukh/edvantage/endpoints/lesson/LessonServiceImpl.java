package kh.farrukh.edvantage.endpoints.lesson;

import kh.farrukh.edvantage.endpoints.course.CourseRepository;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.utils.checkers.CheckUtils;
import kh.farrukh.edvantage.utils.pagination.PagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public PagedList<Lesson> getLessonsOfCourse(long courseId, int pageNumber, int pageSize) {
        CheckUtils.checkCourseId(courseId, courseRepository);
        Page<Lesson> page = lessonRepository.findByCourse_Id(courseId, PageRequest.of(pageNumber - 1, pageSize));
        return new PagedList<>(page);
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
        Lesson lesson = new Lesson(lessonDTO, userRepository);
        lesson.setCourseById(courseId, courseRepository);
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson updateLesson(long courseId, long id, LessonDTO lessonDTO) {
        Lesson existingLesson = lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lesson", "id", id)
        );
        existingLesson.setTitle(lessonDTO.getTitle());
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
