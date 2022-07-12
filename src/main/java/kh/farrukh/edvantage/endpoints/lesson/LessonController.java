package kh.farrukh.edvantage.endpoints.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_LESSON;

@Controller
@RequestMapping(ENDPOINT_LESSON)
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public String lessonsList(
            @PathVariable long courseId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        model.addAttribute("course_id", courseId);
        model.addAttribute("lessons", lessonService.getLessonsOfCourse(courseId, pageNumber, pageSize));
        return "lessons";
    }

    @GetMapping("/new")
    public String addLessonFrom(@PathVariable long courseId, Model model) {
        LessonDTO lessonDTO = new LessonDTO();
        model.addAttribute("course_id", courseId);
        model.addAttribute("lesson", lessonDTO);
        return "add_lesson";
    }

    @PostMapping
    public String addLesson(@PathVariable long courseId, @ModelAttribute("lesson") LessonDTO lessonDTO) {
        lessonService.addLesson(courseId, lessonDTO);
        return "redirect:/courses/" + courseId + "/lessons";
    }

    @GetMapping("edit/{id}")
    public String editLessonFrom(@PathVariable long courseId, @PathVariable long id, Model model) {
        model.addAttribute("course_id", courseId);
        model.addAttribute("lesson", lessonService.getLessonById(courseId, id));
        return "edit_lesson";
    }

    @PostMapping("edit/{id}")
    public String updateCourse(@PathVariable long courseId, @PathVariable long id, @ModelAttribute("course") LessonDTO lessonDTO) {
        lessonService.updateLesson(courseId, id, lessonDTO);
        return "redirect:/courses/" + courseId + "/lessons";
    }

    @GetMapping("delete/{id}")
    public String deleteCourse(@PathVariable long courseId, @PathVariable long id) {
        lessonService.deleteLessonById(courseId, id);
        return "redirect:/courses/" + courseId + "/lessons";
    }
}
