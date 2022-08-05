package kh.farrukh.edvantage.endpoints.user;

import kh.farrukh.edvantage.utils.pagination.PagedList;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    PagedList<AppUser> getUsers(int pageNumber, int pageSize);

    List<AppUser> getUsersList();

    AppUser getUserById(long id);

    AppUser addUser(AppUserDTO userDTO);

    AppUser updateUser(long id, AppUserDTO userDTO);

    void deleteUserById(long id);
}
