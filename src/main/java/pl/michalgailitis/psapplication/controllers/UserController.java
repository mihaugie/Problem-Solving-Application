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
import pl.michalgailitis.psapplication.model.user.specifications.Role;
import pl.michalgailitis.psapplication.services.users.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(WebConstants.USERS_URL)
public class UserController {

    private final UserService userService;

    @GetMapping
    public String showUsers(final ModelMap modelMap) {
        modelMap.addAllAttributes(Map.of(
                "usersList", userService.getAllUsers(),
                "roles", Role.allTypes()));
        return WebConstants.USERS_URL;
    }

    @GetMapping(WebConstants.CREATE_URL)
    public String getUserForm(final ModelMap modelMap) {
        UserForm userForm = new UserForm();
        modelMap.addAllAttributes(Map.of("userForm", userForm));
        return WebConstants.NEWUSER_URL;
    }

    @PostMapping(WebConstants.CREATE_URL)
    public String createUser(@Valid @ModelAttribute("userForm") final UserForm userForm, final Errors errors) {
        if (errors.hasErrors()) {
            return WebConstants.NEWUSER_URL;
        }
        userService.createUser(userForm);
        return "redirect:/";
    }

    @PostMapping("/updateForm")
    public String getUpdatedUserForm(final ModelMap modelMap, final User currentUser) {
        modelMap.addAllAttributes(Map.of(
                "currentUser", currentUser,
                "roles", Role.allTypes()));
        return "userToUpdate";
    }

    @PostMapping("/update")
    public String updateUserForm(final User updatedUser) {
        userService.updateUserPartially(updatedUser);
        return "redirect:/users";
    }
}
