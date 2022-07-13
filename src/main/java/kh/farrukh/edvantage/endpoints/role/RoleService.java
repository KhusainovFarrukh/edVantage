package kh.farrukh.edvantage.endpoints.role;

import kh.farrukh.edvantage.utils.pagination.PagedList;

public interface RoleService {
    PagedList<Role> getRoles(int pageNumber, int pageSize);

    Role getRoleById(long id);

    Role addRole(RoleDTO roleDTO);

    Role updateRole(long id, RoleDTO roleDTO);

    void deleteRoleById(long id);
}
