package kh.farrukh.edvantage.endpoints.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static kh.farrukh.edvantage.utils.constants.Regex.REGEX_EMAIL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    @NotBlank
    private String name;
    @Email(regexp = REGEX_EMAIL)
    private String email;
    private String password;
    private long role;
}
