package kh.farrukh.edvantage.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("course", courseDTO);
        return "add_course";
    }

    @PostMapping
    public String addCourse(@ModelAttribute("course") CourseDTO courseDTO) {
        courseService.addCourse(courseDTO);
        return "redirect:/courses";
    }

    @GetMapping("edit/{id}")
    public String editCourseFrom(@PathVariable long id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        return "edit_course";
    }

    @PostMapping("edit/{id}")
    public String updateCourse(@PathVariable long id, @ModelAttribute("course") CourseDTO courseDTO) {
        courseService.updateCourse(id, courseDTO);
        return "redirect:/courses";
    }

    @GetMapping("delete/{id}")
    public String deleteCourse(@PathVariable long id) {
        courseService.deleteCourseById(id);
        return "redirect:/courses";
    }
}
