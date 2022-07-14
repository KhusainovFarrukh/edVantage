package kh.farrukh.edvantage.endpoints.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_USER;

@Controller
@RequestMapping(ENDPOINT_USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String usersList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        model.addAttribute("users", userService.getUsers(pageNumber, pageSize));
        return "users";
    }

    @GetMapping("/new")
    public String addUserForm(Model model) {
        AppUserDTO userDTO = new AppUserDTO();
        model.addAttribute("user", userDTO);
        return "add_user";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") AppUserDTO userDTO) {
        userService.addUser(userDTO);
        return "redirect:/users";
    }

    @GetMapping("edit/{id}")
    public String editUserFrom(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit_user";
    }

    @PostMapping("edit/{id}")
    public String updateUser(@PathVariable long id, @ModelAttribute("user") AppUserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return "redirect:/users";
    }

    @GetMapping("delete/{id}")
    public String deleteUsers(@PathVariable long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
