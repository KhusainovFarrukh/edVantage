package kh.farrukh.edvantage;

import kh.farrukh.edvantage.endpoints.course.CourseRepository;
import kh.farrukh.edvantage.endpoints.lesson.LessonRepository;
import kh.farrukh.edvantage.endpoints.role.RoleDTO;
import kh.farrukh.edvantage.endpoints.role.RoleService;
import kh.farrukh.edvantage.endpoints.role.UserFeatures;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class EdVantageApplication implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(EdVantageApplication.class, args);
    }

    @Override
    public void run(String... args) {
/*
        courseRepository.save(new Course("Kotlin basics", 166.99));
        courseRepository.save(new Course("Spring Boot for professionals", 250.00));
        courseRepository.save(new Course("From Java-8 to Java-18", 0.99));
        courseRepository.save(new Course("English for IT", 59.99));
        courseRepository.save(new Course("Data Science", 109.90));
*/
/*        roleService.addRole(new RoleDTO(
                "admin",
                Set.of(UserFeatures.values()))
        );
        roleService.addRole(new RoleDTO(
                "teacher",
                Set.of(
                        UserFeatures.CREATE_COURSE,
                        UserFeatures.GET_COURSE,
                        UserFeatures.DELETE_COURSE,
                        UserFeatures.UPDATE_COURSE,
                        UserFeatures.CREATE_LESSON,
                        UserFeatures.GET_LESSON,
                        UserFeatures.UPDATE_LESSON,
                        UserFeatures.DELETE_LESSON,
                        UserFeatures.GET_USER,
                        UserFeatures.UPDATE_USER
                )
        ));
        roleService.addRole(new RoleDTO(
                "student",
                Set.of(
                        UserFeatures.GET_COURSE,
                        UserFeatures.GET_LESSON,
                        UserFeatures.GET_USER,
                        UserFeatures.UPDATE_USER
                )
        ));*/
    }
}
