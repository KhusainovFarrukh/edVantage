package kh.farrukh.edvantage;

import kh.farrukh.edvantage.endpoints.course.CourseService;
import kh.farrukh.edvantage.endpoints.lesson.LessonService;
import kh.farrukh.edvantage.endpoints.role.RoleService;
import kh.farrukh.edvantage.endpoints.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class EdVantageApplication implements CommandLineRunner {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final RoleService roleService;
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(EdVantageApplication.class, args);
    }

    @Override
    public void run(String... args) {
/*
        courseService.addCourse(new CourseDTO("Kotlin basics", 166.99));
        courseService.addCourse(new CourseDTO("Spring Boot for professionals", 250.00));
        courseService.addCourse(new CourseDTO("From Java-8 to Java-18", 0.99));
        courseService.addCourse(new CourseDTO("English for IT", 59.99));
        courseService.addCourse(new CourseDTO("Data Science", 109.90));
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

/*
        userService.addUser(new AppUserDTO(
                "Farrukh Khusainov",
                "farrukh@mail.com",
                "12345678",
                1
        ));
        userService.addUser(new AppUserDTO(
                "Hamdam Xudayberganov",
                "hamdam@mail.com",
                "12345678",
                2
        ));
        userService.addUser(new AppUserDTO(
                "AmigosCode",
                "amigos@mail.com",
                "12345678",
                2
        ));
        userService.addUser(new AppUserDTO(
                "Ramesh Fedatare",
                "ramesh@mail.com",
                "12345678",
                2
        ));
        userService.addUser(new AppUserDTO(
                "User Userov",
                "user@mail.com",
                "12345678",
                3
        ));
        userService.addUser(new AppUserDTO(
                "Lorem Ipsumov",
                "lorem@mail.com",
                "12345678",
                3
        ));
        userService.addUser(new AppUserDTO(
                "Test Testov",
                "test@mail.com",
                "12345678",
                3
        ));
*/
    }
}
