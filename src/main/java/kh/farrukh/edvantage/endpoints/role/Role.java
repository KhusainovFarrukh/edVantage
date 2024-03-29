package kh.farrukh.edvantage.endpoints.role;

import kh.farrukh.edvantage.base.entity.EntityWithId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends EntityWithId {

    @NotBlank
    @Column(unique = true)
    private String title;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "permissions")
    private Set<Permission> permissions = Collections.emptySet();

    public Role(RoleDTO roleDTO) {
        BeanUtils.copyProperties(roleDTO, this);
    }
}
