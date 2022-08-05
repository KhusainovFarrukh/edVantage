package kh.farrukh.edvantage.endpoints.role;

import kh.farrukh.edvantage.jwt.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_ROLE;

@Controller
@RequestMapping(ENDPOINT_ROLE)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasAuthority('GET_ROLE')")
    @GetMapping
    public String rolesList(
            @CurrentUser Role role,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize,
            Model model
    ) {
        model.addAttribute("roles", roleService.getRoles(pageNumber, pageSize));
        model.addAttribute("currentPermissions", role.getPermissions().stream().map(Enum::name).toList());
        return "roles";
    }

    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @GetMapping("/new")
    public String addRoleFrom(Model model) {
        RoleDTO roleDTO = new RoleDTO();
        model.addAttribute("role", roleDTO);
        model.addAttribute("permissions", roleService.getAllPermissions());
        return "add_role";
    }

    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping
    public String addRole(@ModelAttribute("role") RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return "redirect:/roles";
    }

    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    @GetMapping("edit/{id}")
    public String editRoleFrom(@PathVariable long id, Model model) {
        model.addAttribute("role", roleService.getRoleById(id));
        model.addAttribute("permissions", roleService.getAllPermissions());
        return "edit_role";
    }

    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    @PostMapping("edit/{id}")
    public String updateRole(@PathVariable long id, @ModelAttribute("role") RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return "redirect:/roles";
    }

    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @GetMapping("delete/{id}")
    public String deleteRole(@PathVariable long id) {
        roleService.deleteRoleById(id);
        return "redirect:/roles";
    }
}
