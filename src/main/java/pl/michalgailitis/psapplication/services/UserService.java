package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser (final User providedUser){
        final User user = userMapper.toUser(providedUser);

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String email) {
        return userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException(String.format("User with id %s does not exist", email)));
    }

    public void deleteUser(final String email){
        userRepository.deleteById(email);
    }

    public User findByNameAndSurename( final String userNameSurename){
        return userRepository.findUserByNameSurename(userNameSurename);
    }

}
