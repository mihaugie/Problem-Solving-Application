package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.model.TicketForm;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.users.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String getTicketDetails(final ModelMap modelMap, @PathVariable final Long id, @AuthenticationPrincipal Principal principal) {
        final Ticket selectedTicket = ticketService.getTicketById(id);

        modelMap.addAllAttributes(Map.of(
                "selectedticket", selectedTicket,
                "comment", new Comment(),
                "currentuser", userService.getUserById(principal.getName())));
        return "ticketdetails";
    }

    @GetMapping("/all")
    public String getAllTickets(ModelMap modelMap) {
        modelMap.addAttribute("tickets", ticketService.getAllTickets());
        return "alltickets";
    }

    @GetMapping
    public String getNewTicketForm(ModelMap modelMap) {
        modelMap.addAllAttributes(Map.of(
                "ticketForm", new TicketForm(),
                "tickettypes", TicketType.allTypes(),
                "users", userService.getAllUsers()));
        return "newTicket";
    }

    @PostMapping("/add")
    public String addNewTicketForm(@Valid @ModelAttribute("ticketForm") final TicketForm ticketForm, final Errors errors) {
        if (errors.hasErrors()) {
            return "newTicket";
        }
        ticketService.createTicket(ticketForm);
        return "redirect:/";
    }

    @PostMapping("/{id}/comment/new")
    public String addNewCommentForm(@PathVariable Long id, Comment comment) {
        ticketService.createComment(id, comment);
        return "redirect:/tickets/{id}";
    }

    @PostMapping("/{id}/close")
    public String closeTicket(@PathVariable Long id) {
        ticketService.closeTicket(id);
        return "redirect:/";
    }
}
