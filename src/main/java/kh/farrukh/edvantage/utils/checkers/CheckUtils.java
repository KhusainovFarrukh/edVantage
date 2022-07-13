package kh.farrukh.edvantage.utils.checkers;

import kh.farrukh.edvantage.endpoints.course.CourseRepository;
import kh.farrukh.edvantage.endpoints.lesson.LessonRepository;
import kh.farrukh.edvantage.endpoints.role.RoleRepository;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.DuplicateResourceException;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;

public class CheckUtils {

    public static void checkCourseId(long courseId, CourseRepository courseRepository) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course", "id", courseId);
        }
    }

    public static void checkLessonId(long lessonId, LessonRepository lessonRepository) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson", "id", lessonId);
        }
    }

    public static void checkRoleId(long roleId, RoleRepository roleRepository) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role", "id", roleId);
        }
    }

    public static void checkRoleIsUnique(String title, RoleRepository roleRepository) {
        if (roleRepository.existsByTitle(title)) {
            throw new DuplicateResourceException("Role", "title", title);
        }
    }

    public static void checkUserId(long userId, UserRepository userRepository) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }

    public static void checkUserIsUnique(String email, UserRepository userRepository) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("User", "email", email);
        }
    }
}
