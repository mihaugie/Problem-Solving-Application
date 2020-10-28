package pl.michalgailitis.psapplication.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.services.UserService;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable(name = "email") String email) throws Exception {
        return userService.getUserById(email);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
    @DeleteMapping("/{mail}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable final String mail){
        userService.deleteUser(mail);
    }

}
