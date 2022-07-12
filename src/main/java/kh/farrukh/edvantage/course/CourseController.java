package kh.farrukh.edvantage.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_COURSE;

@Controller
@RequestMapping(ENDPOINT_COURSE)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public String coursesList(Model model) {
        model.addAttribute("courses", courseService.getCourses());
        return "courses";
    }

    @GetMapping("/new")
    public String addCourseFrom(Model model) {
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("course_dto", courseDTO);
        return "add_course";
    }

    @PostMapping
    public String addCourse(@ModelAttribute("course_dto") CourseDTO courseDTO) {
        courseService.addCourse(courseDTO);
        return "redirect:/courses";
    }
}
