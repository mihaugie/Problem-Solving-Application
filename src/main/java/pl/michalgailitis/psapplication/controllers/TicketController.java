package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.util.List;
import java.util.Map;

@Slf4j
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

    @GetMapping("/add")
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

    @GetMapping(value = "/")
    public String viewIndexPage() {
        log.info("Redirecting the index page to the controller method for fetching the employees in a "
                + "paginated fashion.");
        // During the index page we are using the sort-field as id and sort-dir as asc.
        return "redirect:/tickets/page/1?sort-field=id&sort-dir=asc";
    }

    // URL - http://localhost:10092/page/1?sort-field=firstName&sort-dir=desc
    @GetMapping(value = "/page/{page-number}")
    public String findPaginated(@PathVariable(name = "page-number") final int pageNo,
                                @RequestParam(name = "sort-field") final String sortField,
                                @RequestParam(name = "sort-dir") final String sortDir,
                                final Model model) {
        log.info("Getting the employees in a paginated way for page-number = {}, sort-field = {}, and "
                + "sort-direction = {}.", pageNo, sortField, sortDir);
        // Hardcoding the page-size to 15.
        final int pageSize = 5;
        final Page<Ticket> page = ticketService.findPaginated(pageNo, pageSize, sortField, sortDir);
        final List<Ticket> listTickets = page.getContent();

        // Creating the model response.
        // Note for simplicity purpose we are not making the use of ResponseDto here.
        // In ideal cases the response will be encapsulated in a class.
        // pagination parameters
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        // sorting parameters
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        // employees
        model.addAttribute("listTickets", listTickets);
        return "ticketsView";
    }

}
