package kh.farrukh.edvantage.endpoints.user;

import kh.farrukh.edvantage.base.entity.EntityWithId;
import kh.farrukh.edvantage.endpoints.role.Role;
import kh.farrukh.edvantage.endpoints.role.RoleRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends EntityWithId {

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @ManyToOne
    private Role role;

    public AppUser(AppUserDTO userDTO, RoleRepository roleRepository) {
        BeanUtils.copyProperties(userDTO, this, "password");
        this.role = roleRepository.findById(userDTO.getRole()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "id", userDTO.getRole())
        );
    }
}
