package kh.farrukh.edvantage.endpoints.user;

import kh.farrukh.edvantage.endpoints.role.Role;
import kh.farrukh.edvantage.endpoints.role.RoleRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.DuplicateResourceException;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.utils.checkers.CheckUtils;
import kh.farrukh.edvantage.utils.pagination.PagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                // TODO: 8/8/22 needs i18n
                () -> new UsernameNotFoundException("Wrong email")
        );
    }

    @Override
    public PagedList<AppUser> getUsers(int pageNumber, int pageSize) {
        return new PagedList<>(userRepository.findAll(PageRequest.of(pageNumber - 1, pageSize)));
    }

    @Override
    public List<AppUser> getUsersList() {
        return userRepository.findAll();
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
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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
