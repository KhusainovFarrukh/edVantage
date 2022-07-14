package kh.farrukh.edvantage.endpoints.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_ROLE;

@Controller
@RequestMapping(ENDPOINT_ROLE)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public String rolesList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        model.addAttribute("roles", roleService.getRoles(pageNumber, pageSize));
        return "roles";
    }

    @GetMapping("/new")
    public String addRoleFrom(Model model) {
        RoleDTO roleDTO = new RoleDTO();
        model.addAttribute("role", roleDTO);
        return "add_role";
    }

    @PostMapping
    public String addRole(@ModelAttribute("role") RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return "redirect:/roles";
    }

    @GetMapping("edit/{id}")
    public String editRoleFrom(@PathVariable long id, Model model) {
        model.addAttribute("role", roleService.getRoleById(id));
        return "edit_role";
    }

    @PostMapping("edit/{id}")
    public String updateRole(@PathVariable long id, @ModelAttribute("role") RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return "redirect:/roles";
    }

    @GetMapping("delete/{id}")
    public String deleteRole(@PathVariable long id) {
        roleService.deleteRoleById(id);
        return "redirect:/roles";
    }
}
