package pl.michalgailitis.psapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForm {

    @NotBlank
    @Email(message = "Needs to have email format")
    private String email;
    @NotNull
    @Length(min = 3, message = "Minumum password length is 3")
    private String password;
    @NotBlank
    @Length(min = 3, message = "Minimum length is 3")
    private String nameSurename;

}
