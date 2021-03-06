package kh.farrukh.edvantage.endpoints.lesson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourse_Id(long courseId);

    Page<Lesson> findByCourse_Id(long courseId, Pageable pageable);
}
