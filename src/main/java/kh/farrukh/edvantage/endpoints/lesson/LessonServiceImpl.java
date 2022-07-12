package kh.farrukh.edvantage.endpoints.lesson;

import kh.farrukh.edvantage.endpoints.course.CourseRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Lesson> getLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLessonById(long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lesson", "id", id)
        );
    }

    @Override
    public Lesson addLesson(LessonDTO lessonDTO) {
        return lessonRepository.save(new Lesson(lessonDTO));
    }

    @Override
    public Lesson updateLesson(long id, LessonDTO lessonDTO) {
        Lesson existingLesson = lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lesson", "id", id)
        );
        existingLesson.setTitle(lessonDTO.getTitle());
        existingLesson.setAuthor(lessonDTO.getAuthor());
        existingLesson.setTextBody(lessonDTO.getTextBody());
        existingLesson.setCourseById(lessonDTO.getCourse(), courseRepository);
        return lessonRepository.save(existingLesson);
    }

    @Override
    public void deleteLessonById(long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson", "id", id);
        }
        lessonRepository.deleteById(id);
    }
}
