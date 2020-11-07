package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.domain.Role;
import pl.michalgailitis.psapplication.domain.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.UserInfoService;
import pl.michalgailitis.psapplication.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserInfoService userInfoService;
    private final TicketService ticketService;
    private final UserService userService;

    private final Status USERDASHBOARDSTATUS = Status.OPEN;

    @GetMapping
    public String userDashboard(final ModelMap modelMap) throws Exception {


        String currentUserName = userInfoService.getCurrentUserName();
        User currentUser = userService.getUserById(currentUserName);

        modelMap.addAttribute("username", currentUser);

        List<Ticket> ticketByAuthor = ticketService.getTicketByAuthor(currentUser);
        modelMap.addAttribute("byauthor", ticketByAuthor);

        List<Ticket> ticketByAuthorOrResponsible = ticketService.getTicketByAuthorOrResponsibleAndStatus(currentUser, currentUser, USERDASHBOARDSTATUS);
        modelMap.addAttribute("usertickets", ticketByAuthorOrResponsible);

        int noOfOpenTicketsForLoggedUser = ticketByAuthorOrResponsible.size();
        modelMap.addAttribute("noofopentickets", noOfOpenTicketsForLoggedUser);

        return "userdashboard";

    }

}
