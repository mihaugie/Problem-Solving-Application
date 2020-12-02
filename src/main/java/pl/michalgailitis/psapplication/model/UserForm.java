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

    @NotBlank(message = "Please provide email")
    @Email(message = "Needs to have email format")
    private String email;

    @NotNull(message = "Please provide password")
    @Length(min = 3, message = "Minumum password length is 3")
    private String password;

    @NotBlank(message = "Please provide name and lastname")
    @Length(min = 3, message = "Minimum length is 3")
    private String nameSurename;

}
