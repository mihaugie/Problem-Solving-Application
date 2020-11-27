package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
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
                WebConstants.USERS_LIST_MODEL, userService.getAllUsers(),
                WebConstants.ROLES_MODEL, Role.allTypes()));
        return WebConstants.USERS_URL;
    }

    @GetMapping(WebConstants.CREATE_URL)
    public String getUserForm(final ModelMap modelMap) {
        UserForm userForm = new UserForm();
        modelMap.addAllAttributes(Map.of(WebConstants.USER_FORM_MODEL, userForm));
        return WebConstants.NEWUSER_URL;
    }

    @PostMapping(WebConstants.CREATE_URL)
    public String createUser(@Valid @ModelAttribute(WebConstants.USER_FORM_MODEL) final UserForm userForm, final Errors errors) {
        if (errors.hasErrors()) {
            return WebConstants.NEWUSER_URL;
        }
        userService.createUser(userForm);
        return "redirect:/";
    }

    @PostMapping(WebConstants.UPDATE_FORM_URL)
    public String getUpdatedUserForm(final ModelMap modelMap, final User currentUser) {
        modelMap.addAllAttributes(Map.of(
                WebConstants.CURRENT_USER_MODEL, currentUser,
                WebConstants.ROLES_MODEL, Role.allTypes()));
        return WebConstants.USER_TO_UPDATE_VIEW;
    }

    @PostMapping(WebConstants.UPDATE_URL)
    public RedirectView updateUserForm(final User updatedUser) {
        userService.updateUserPartially(updatedUser);
        return new RedirectView(WebConstants.USERS_URL);
    }
}
