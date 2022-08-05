package kh.farrukh.edvantage.endpoints.course;

import kh.farrukh.edvantage.endpoints.user.UserService;
import kh.farrukh.edvantage.endpoints.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_COURSE;

@Controller
@RequestMapping(ENDPOINT_COURSE)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;

    // TODO: 8/4/22 optimize code
    @PreAuthorize("hasAuthority('GET_COURSE')")
    @GetMapping
    public String coursesList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        model.addAttribute("courses", courseService.getCourses(pageNumber, pageSize));
        return "courses";
    }

    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    @GetMapping("/new")
    public String addCourseForm(Model model) {
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("course", courseDTO);
        model.addAttribute("users", userService.getUsersList());
        return "add_course";
    }

    @PreAuthorize("hasAuthority('CREATE_COURSE')")
    @PostMapping
    public String addCourse(@ModelAttribute("course") CourseDTO courseDTO) {
        courseService.addCourse(courseDTO);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE')")
    @GetMapping("edit/{id}")
    public String editCourseForm(@PathVariable long id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        model.addAttribute("users", userService.getUsersList());
        return "edit_course";
    }

    @PreAuthorize("hasAuthority('UPDATE_COURSE')")
    @PostMapping("edit/{id}")
    public String updateCourse(@PathVariable long id, @ModelAttribute("course") CourseDTO courseDTO) {
        courseService.updateCourse(id, courseDTO);
        return "redirect:/courses";
    }

    @PreAuthorize("hasAuthority('DELETE_COURSE')")
    @GetMapping("delete/{id}")
    public String deleteCourse(@PathVariable long id) {
        courseService.deleteCourseById(id);
        return "redirect:/courses";
    }
}
