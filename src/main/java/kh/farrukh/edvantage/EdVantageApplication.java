package kh.farrukh.edvantage;

import kh.farrukh.edvantage.endpoints.course.CourseDTO;
import kh.farrukh.edvantage.endpoints.course.CourseService;
import kh.farrukh.edvantage.endpoints.lesson.LessonService;
import kh.farrukh.edvantage.endpoints.role.Permission;
import kh.farrukh.edvantage.endpoints.role.RoleDTO;
import kh.farrukh.edvantage.endpoints.role.RoleService;
import kh.farrukh.edvantage.endpoints.user.AppUserDTO;
import kh.farrukh.edvantage.endpoints.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;

import java.util.Set;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_ERROR;

@SpringBootApplication
@RequiredArgsConstructor
public class EdVantageApplication implements CommandLineRunner {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final RoleService roleService;
    private final UserService userService;

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> registry.addErrorPages(new ErrorPage(ENDPOINT_ERROR));
    }

    public static void main(String[] args) {
        SpringApplication.run(EdVantageApplication.class, args);
    }

    @Override
    public void run(String... args) {
        courseService.addCourse(new CourseDTO("Kotlin basics", 166.99));
        courseService.addCourse(new CourseDTO("Spring Boot for professionals", 250.00));
        courseService.addCourse(new CourseDTO("From Java-8 to Java-18", 0.99));
        courseService.addCourse(new CourseDTO("English for IT", 59.99));
        courseService.addCourse(new CourseDTO("Data Science", 109.90));
        roleService.addRole(new RoleDTO(
                "admin",
                Set.of(Permission.values()))
        );
        roleService.addRole(new RoleDTO(
                "teacher",
                Set.of(
                        Permission.CREATE_COURSE,
                        Permission.GET_COURSE,
                        Permission.DELETE_COURSE,
                        Permission.UPDATE_COURSE,
                        Permission.CREATE_LESSON,
                        Permission.GET_LESSON,
                        Permission.UPDATE_LESSON,
                        Permission.DELETE_LESSON,
                        Permission.GET_USER,
                        Permission.UPDATE_USER
                )
        ));
        roleService.addRole(new RoleDTO(
                "student",
                Set.of(
                        Permission.GET_COURSE,
                        Permission.GET_LESSON,
                        Permission.GET_USER,
                        Permission.UPDATE_USER
                )
        ));
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
    }
}
