package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.services.tickets.TicketService;
import pl.michalgailitis.psapplication.services.users.UserInfoService;
import pl.michalgailitis.psapplication.services.users.UserService;

import java.security.Principal;
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
    public String userDashboard(final ModelMap modelMap, @AuthenticationPrincipal Principal principal, String keyword) {
        User currentUser = userService.getUserById(principal.getName());
        Set<Ticket> ticketByAuthorOrResponsible = ticketService.getTicketForUserDashboard(WebConstants.USERSTATUS_OPEN, principal.getName());
        int noOfOpenTicketsForLoggedUser = ticketByAuthorOrResponsible.size();

        if (keyword == "") {
            modelMap.addAttribute(WebConstants.USER_TICKETS_MODEL, ticketByAuthorOrResponsible);
        } else {
            modelMap.addAttribute(WebConstants.USER_TICKETS_MODEL, ticketService.getFilteredTicketsForUserDashboard(WebConstants.USERSTATUS_OPEN, principal.getName(), keyword));
        }

        modelMap.addAllAttributes(Map.of(
                WebConstants.CURRENT_USER_MODEL, currentUser,
                WebConstants.USER_TICKETS_MODEL, ticketByAuthorOrResponsible,
                WebConstants.NO_OF_OPEN_TICKETS_MODEL, noOfOpenTicketsForLoggedUser));
        return WebConstants.USER_DASHBOARD_VIEW;
    }
}
