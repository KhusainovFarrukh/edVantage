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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends EntityWithId implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissions().stream().map(
                feature -> new SimpleGrantedAuthority(feature.name())
        ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
