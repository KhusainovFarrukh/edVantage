package kh.farrukh.edvantage.endpoints.user;

import kh.farrukh.edvantage.endpoints.role.Role;
import kh.farrukh.edvantage.endpoints.role.RoleRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.DuplicateResourceException;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.utils.checkers.CheckUtils;
import kh.farrukh.edvantage.utils.pagination.PagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public PagedList<AppUser> getUsers(int pageNumber, int pageSize) {
        return new PagedList<>(userRepository.findAll(PageRequest.of(pageNumber - 1, pageSize)));
    }

    @Override
    public AppUser getUserById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
    }

    @Override
    public AppUser addUser(AppUserDTO userDTO) {
        CheckUtils.checkUserIsUnique(userDTO.getEmail(), userRepository);
        AppUser user = new AppUser(userDTO, roleRepository);
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(long id, AppUserDTO userDTO) {
        AppUser existingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        if (!userDTO.getEmail().equals(existingUser.getEmail()) &&
                userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("User", "email", userDTO.getEmail());
        }
        Role role = roleRepository.findById(userDTO.getRole()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", userDTO.getRole())
        );

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(role);

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserById(long id) {
        CheckUtils.checkUserId(id, userRepository);
        userRepository.deleteById(id);
    }
}
