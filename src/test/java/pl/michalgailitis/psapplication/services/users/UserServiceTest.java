package pl.michalgailitis.psapplication.services.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.UserForm;
import pl.michalgailitis.psapplication.model.user.specifications.Role;
import pl.michalgailitis.psapplication.repository.UserRepository;
import pl.michalgailitis.psapplication.services.mappers.UserMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String EMAIL = "test@email.com";

    private static final User TEST_USER = User.builder()
            .email(EMAIL)
            .build();

    private static final UserForm TEST_USER_FORM = UserForm.builder()
            .email(EMAIL)
            .build();

    private static final User MAPPED_USER = User.builder()
            .email("test2@email.com")
            .build();

    private static final User SAVED_USER = User.builder()
            .email("test3@email.com")
            .build();


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    //MB mockiem są tylko zależności
    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void shouldReturnCreatedUser() {
        //given
        when(userMapper.createUser(TEST_USER_FORM)).thenReturn(MAPPED_USER);
        when(userRepository.save(MAPPED_USER)).thenReturn(SAVED_USER);

        //when
        User actualUser = userService.createUser(TEST_USER_FORM);

        //then
        assertThat(actualUser).isEqualTo(SAVED_USER);
    }

    @Test
    void shouldFindUserById() {
        //given
        when(userRepository.findById(EMAIL)).thenReturn(Optional.of(TEST_USER));

        //when
        User userById = userService.getUserById(EMAIL);

        //then
        assertThat(userById).isEqualTo(TEST_USER);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        //given
        when(userRepository.findById(EMAIL)).thenReturn(Optional.empty());

        //when + then przy testowaniu wyjątków
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> userService.getUserById(EMAIL))
                .withMessage("User with id test@email.com does not exist");
    }

    @Test
    void shouldUpdateUserPartiallyWithUpdatedFields() {
        User userWithFieldsToBeUpdated = User.builder()
                .email(EMAIL)
                .password("pass1")
                .nameSurename("Adam")
                .role(Role.USER)
                .build();
        User existingUser = User.builder()
                .email(EMAIL)
                .build();
        when(userRepository.findByEmail(EMAIL)).thenReturn(existingUser);

        //when
        userService.updateUserPartially(userWithFieldsToBeUpdated);

        //testowanie, ze zostala wywolana metoda save
        //then
//        verify(userRepository).save(User.builder()
//                .email(EMAIL)
//                .password("pass1")
//                .nameSurename("Adam")
//                .role(Role.USER)
//                .build());
        verify(userRepository).save(userArgumentCaptor.capture());
        User userArgumentUser = userArgumentCaptor.getValue();
        assertThat(userArgumentUser.getPassword()).isEqualTo("pass1");
        assertThat(userArgumentUser.getRole()).isEqualTo(Role.USER);
        assertThat(userArgumentUser.getNameSurename()).isEqualTo("Adam");
    }
}