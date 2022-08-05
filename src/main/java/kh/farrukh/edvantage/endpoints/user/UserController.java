package kh.farrukh.edvantage.endpoints.user;

import kh.farrukh.edvantage.endpoints.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_USER;

@Controller
@RequestMapping(ENDPOINT_USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @PreAuthorize("hasAuthority('GET_USER')")
    @GetMapping
    public String usersList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        model.addAttribute("users", userService.getUsers(pageNumber, pageSize));
        return "users";
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @GetMapping("/new")
    public String addUserForm(Model model) {
        AppUserDTO userDTO = new AppUserDTO();
        model.addAttribute("user", userDTO);
        model.addAttribute("roles", roleService.getRolesList());
        return "add_user";
    }

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public String addUser(@ModelAttribute("user") AppUserDTO userDTO) {
        userService.addUser(userDTO);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @GetMapping("edit/{id}")
    public String editUserFrom(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getRolesList());
        return "edit_user";
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PostMapping("edit/{id}")
    public String updateUser(@PathVariable long id, @ModelAttribute("user") AppUserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @GetMapping("delete/{id}")
    public String deleteUsers(@PathVariable long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
