package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.model.TicketForm;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.TicketTypeService;
import pl.michalgailitis.psapplication.services.users.UserInfoService;
import pl.michalgailitis.psapplication.services.users.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketTypeService ticketTypeService;
    private final UserInfoService userInfoService;
    private final UserService userService;

    //MB Stringi na stałe - w ramach pól w tej klasie

    @GetMapping("/{id}")
    public String getTicketDetails(final ModelMap modelMap, @PathVariable final Long id) {
        final Ticket selectedTicket = ticketService.getTicketById(id);

        modelMap.addAllAttributes(Map.of(
                "selectedticket", selectedTicket,
                "comment", new Comment(),
                "currentuser", userService.getUserById(userInfoService.getCurrentUserId())));
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
                "tickettypes", ticketTypeService.getTicketTypes(),
                "users", userService.getAllUsers()));
        return "newTicket";
    }

//MB - mapowanie wyrzucić do serwisu - lub do model - klasa TicketForm (Ticket.builder(). ... ) , to samo z User
    // services/mapper/TicketMapper i ten mapper wstrzykiwany jest TicketService do mapowania TicketForm na Ticket

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
