package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.michalgailitis.psapplication.domain.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(final User providedUser){
        final User user = new User();
        user.setEmail(providedUser.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;

    }
}
