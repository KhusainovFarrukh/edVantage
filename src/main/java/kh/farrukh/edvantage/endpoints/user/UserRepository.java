package kh.farrukh.edvantage.endpoints.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);
}
