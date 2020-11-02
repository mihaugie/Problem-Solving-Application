package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.domain.Role;
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

    @GetMapping
    public String userDashboard(final ModelMap modelMap) throws Exception {
        String currentUserName = userInfoService.getCurrentUserName();
        User currentUser = userService.getUserById(currentUserName);

        modelMap.addAttribute("username", currentUserName);


        modelMap.addAttribute("tickets", ticketService.getAllTickets());
        modelMap.addAttribute("ticketsbytitle", ticketService.getTicketByTitle("Ticket title 1"));


        List<Ticket> ticketByAuthor = ticketService.getTicketByAuthor(currentUser);
        modelMap.addAttribute("byauthor", ticketByAuthor);

        List<Ticket> ticketByAuthorOrResponsible = ticketService.getTicketByAuthorOrResponsible(currentUser, currentUser);
        modelMap.addAttribute("usertickets", ticketByAuthorOrResponsible);

        return "userdashboard";

    }


}
