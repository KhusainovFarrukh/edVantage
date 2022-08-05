package kh.farrukh.edvantage.endpoints.lesson;

import kh.farrukh.edvantage.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_LESSON;

@Controller
@RequestMapping(ENDPOINT_LESSON)
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAuthority('GET_LESSON')")
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

    @PreAuthorize("hasAuthority('CREATE_LESSON')")
    @GetMapping("/new")
    public String addLessonForm(@PathVariable long courseId, Model model) {
        LessonDTO lessonDTO = new LessonDTO();
        model.addAttribute("course_id", courseId);
        model.addAttribute("lesson", lessonDTO);
        return "add_lesson";
    }

    @PreAuthorize("hasAuthority('CREATE_LESSON')")
    @PostMapping
    public String addLesson(
            @CurrentUser Long userId,
            @PathVariable long courseId,
            @ModelAttribute("lesson") LessonDTO lessonDTO
    ) {
        lessonDTO.setAuthorId(userId);
        lessonService.addLesson(courseId, lessonDTO);
        return "redirect:/courses/" + courseId + "/lessons";
    }

    @PreAuthorize("hasAuthority('UPDATE_LESSON')")
    @GetMapping("edit/{id}")
    public String editLessonForm(@PathVariable long courseId, @PathVariable long id, Model model) {
        model.addAttribute("course_id", courseId);
        model.addAttribute("lesson", lessonService.getLessonById(courseId, id));
        return "edit_lesson";
    }

    @PreAuthorize("hasAuthority('UPDATE_LESSON')")
    @PostMapping("edit/{id}")
    public String updateLesson(@PathVariable long courseId, @PathVariable long id, @ModelAttribute("course") LessonDTO lessonDTO) {
        lessonService.updateLesson(courseId, id, lessonDTO);
        return "redirect:/courses/" + courseId + "/lessons";
    }

    @PreAuthorize("hasAuthority('DELETE_LESSON')")
    @GetMapping("delete/{id}")
    public String deleteLesson(@PathVariable long courseId, @PathVariable long id) {
        lessonService.deleteLessonById(courseId, id);
        return "redirect:/courses/" + courseId + "/lessons";
    }
}
