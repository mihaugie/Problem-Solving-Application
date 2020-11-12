package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.repository.RoleRepository;
import pl.michalgailitis.psapplication.services.RoleService;
import pl.michalgailitis.psapplication.services.users.UserService;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    //MB teksty do stałej

    @GetMapping
    public String showUsers(final ModelMap modelMap) {

        modelMap.addAllAttributes(Map.of("usersList", userService.getAllUsers(), "roles", roleService.getRoles()));

        return "users";
    }

    //POPRAWIC ALLTICKETS PO POSCIE + PRZETESTOWAC
    //POPRAWIC DROPDOWN USER W FORMULARZU

    @PostMapping("/updateForm")
    public String getUpdatedUserForm(final ModelMap modelMap, final User currentUser){

        modelMap.addAllAttributes(Map.of("currentUser", currentUser, "roles", roleService.getRoles()));
        return "userToUpdate";
    }

    @PostMapping("/update")
    public String updateUserForm(final User updatedUser){
        userService.updateUserPartially(updatedUser);

        return "users";
    }



}
