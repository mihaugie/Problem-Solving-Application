package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PreInsertEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.users.UserInfoService;
import pl.michalgailitis.psapplication.services.users.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserInfoService userInfoService;
    private final TicketService ticketService;
    private final UserService userService;

    @GetMapping
    public String userDashboard(final ModelMap modelMap, @AuthenticationPrincipal Principal principal) {
        String currentUserName = userInfoService.getCurrentUserId();
        User currentUser = userService.getUserById(currentUserName);
        List<Ticket> ticketByAuthor = ticketService.getTicketByAuthor(currentUser);
        Set<Ticket> ticketByAuthorOrResponsible = ticketService.getTicketForUserDashboard(WebConstants.USERSTATUS_OPEN, principal.getName());
        int noOfOpenTicketsForLoggedUser = ticketByAuthorOrResponsible.size();
        modelMap.addAllAttributes(Map.of(
                "username", currentUser,
                "byauthor", ticketByAuthor,
                "usertickets", ticketByAuthorOrResponsible,
                "noofopentickets", noOfOpenTicketsForLoggedUser));
        return "userdashboard";
    }
}