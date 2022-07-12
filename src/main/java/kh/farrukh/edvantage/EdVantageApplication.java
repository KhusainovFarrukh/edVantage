package kh.farrukh.edvantage;

import kh.farrukh.edvantage.course.Course;
import kh.farrukh.edvantage.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class EdVantageApplication implements CommandLineRunner {

    private final CourseRepository courseRepository;

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
    }
}
