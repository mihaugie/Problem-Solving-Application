package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.UserForm;
import pl.michalgailitis.psapplication.services.RoleService;
import pl.michalgailitis.psapplication.services.users.UserService;

import javax.validation.Valid;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final String USERS_URL = "users";
    private final String USER_TO_UPDATE_URL = "userToUpdate";
    private final String ROLES_URL = "roles";
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String showUsers(final ModelMap modelMap) {

        modelMap.addAllAttributes(Map.of(
                "usersList", userService.getAllUsers(),
                ROLES_URL, roleService.getRoles()));
        return USERS_URL;
    }

    @GetMapping("/create")
    public String getUserForm(final ModelMap modelMap) {
        UserForm userForm = new UserForm();
        modelMap.addAllAttributes(Map.of("userForm", userForm));
        return "newUser";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("userForm") final UserForm userForm, final Errors errors) {
        if (errors.hasErrors()) {
            return "newUser";
        }
        userService.createUser(userForm);
        return "redirect:/";
    }

    @PostMapping("/updateForm")
    public String getUpdatedUserForm(final ModelMap modelMap, final User currentUser) {
        modelMap.addAllAttributes(Map.of(
                "currentUser", currentUser,
                ROLES_URL, roleService.getRoles()));
        return USER_TO_UPDATE_URL;
    }

    @PostMapping("/update")
    public String updateUserForm(final User updatedUser) {
        userService.updateUserPartially(updatedUser);
        return "redirect:/users";
    }
}
