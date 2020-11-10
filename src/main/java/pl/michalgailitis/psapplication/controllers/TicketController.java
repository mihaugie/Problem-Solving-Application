package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.model.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.TicketTypeService;
import pl.michalgailitis.psapplication.services.users.UserInfoService;
import pl.michalgailitis.psapplication.services.users.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketTypeService ticketTypeService;
    private final UserInfoService userInfoService;
    private final UserService userService;

    //MB Stringi na stałe
    //DONE: MB zamienic repository na serwis

    @GetMapping("/{id}")
    public String getTicketDetails(final ModelMap modelMap, @PathVariable final Long id) throws Exception {
        final Ticket selectedTicket = ticketService.getTicketById(id);
        modelMap.addAttribute("selectedticket", selectedTicket);
        modelMap.addAttribute("comment", new Comment());
        return "ticketdetails";
    }

    @GetMapping("/all")
    public String getAllTickets(ModelMap modelMap) {
        modelMap.addAttribute("tickets", ticketService.getAllTickets());
        return "alltickets";
    }

    @GetMapping
    public String getNewTicketForm(ModelMap modelMap) {
        modelMap.addAttribute("ticket", new Ticket());
        modelMap.addAttribute("tickettypes", ticketTypeService.getTicketTypes());
        modelMap.addAttribute("users", userService.getAllUsers());
        return "newticket";
    }

//MB - mapowanie wyrzucić do serwisu - lub do model - klasa TicketForm (Ticket.builder(). ... ) , to samo z User
    // services/mapper/TicketMapper i ten mapper wstrzykiwany jest TicketService do mapowania TicketForm na Ticket

    //MB @Auth Principal zamiast User Info Service

    @PostMapping("/addticket")
    public String addNewTicketForm(Ticket ticket, @AuthenticationPrincipal Principal principal) throws Exception {
        ticket.setStatus(Status.OPEN);
        ticket.setAuthor(userService.getUserById(userInfoService.getCurrentUserName()));
        ticket.setResponsible(userService.getUserById(ticket.getResponsible().getEmail()));
        ticketService.createTicket(ticket);
        return "redirect:/";
    }


    @PostMapping("/{id}/comment/new")
    public String addNewCommentForm(@PathVariable Long id, Comment comment) throws Exception {

        comment.setAuthor(userService.getUserById(userInfoService.getCurrentUserName()));

        ticketService.createComment(id, comment);
        return "redirect:/tickets/{id}";
    }


    @PostMapping("/{id}/close")
    public String closeTicket(@PathVariable Long id) throws Exception {

        Ticket ticketToClose = ticketService.getTicketById(id);
        ticketToClose.setStatus(Status.CLOSED);

        ticketService.closeTicket(ticketToClose);
        return "redirect:/";
    }

}
