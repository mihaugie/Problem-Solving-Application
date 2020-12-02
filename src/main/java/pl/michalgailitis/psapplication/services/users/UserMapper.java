package pl.michalgailitis.psapplication.services.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.UserForm;
import pl.michalgailitis.psapplication.model.user.specifications.Role;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User createUser(final UserForm userForm) {
        return User.builder()
                .email(userForm.getEmail())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .nameSurename(userForm.getNameSurename())
                .role(Role.USER)
                .build();
    }
}
