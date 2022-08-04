package kh.farrukh.edvantage.endpoints.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    @NotBlank
    private String title;
    private Set<Permission> permissions = Collections.emptySet();
}