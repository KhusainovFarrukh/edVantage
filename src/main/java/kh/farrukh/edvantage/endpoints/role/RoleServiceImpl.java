package kh.farrukh.edvantage.endpoints.role;

import kh.farrukh.edvantage.exception.custom_exceptions.DuplicateResourceException;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.utils.checkers.CheckUtils;
import kh.farrukh.edvantage.utils.pagination.PagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public PagedList<Role> getRoles(int pageNumber, int pageSize) {
        Page<Role> page = roleRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        return new PagedList<>(page);
    }

    @Override
    public Role getRoleById(long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );
    }

    @Override
    public Role addRole(RoleDTO roleDTO) {
        CheckUtils.checkRoleIsUnique(roleDTO.getTitle(), roleRepository);
        return roleRepository.save(new Role(roleDTO));
    }

    @Override
    public Role updateRole(long id, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", id)
        );

        if (!roleDTO.getTitle().equals(existingRole.getTitle()) &&
                roleRepository.existsByTitle(roleDTO.getTitle())) {
            throw new DuplicateResourceException("Role", "title", roleDTO.getTitle());
        }

        existingRole.setTitle(roleDTO.getTitle());
        existingRole.setUserFeatures(roleDTO.getUserFeatures());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRoleById(long id) {
        CheckUtils.checkRoleId(id, roleRepository);
        roleRepository.deleteById(id);
    }

    @Override
    public List<UserFeature> getAllUserFeatures() {
        return List.of(UserFeature.values());
    }
}
