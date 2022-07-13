package kh.farrukh.edvantage.endpoints.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByTitle(String title);
}
