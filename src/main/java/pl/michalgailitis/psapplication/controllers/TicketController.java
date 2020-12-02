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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.model.TicketForm;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.services.tickets.TicketMapper;
import pl.michalgailitis.psapplication.services.tickets.TicketService;
import pl.michalgailitis.psapplication.services.users.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(WebConstants.TICKETS_URL)
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;


    @GetMapping("/{id}")
    public String getTicketDetails(final ModelMap modelMap, @PathVariable final Long id, @AuthenticationPrincipal Principal principal) throws IOException {
        final TicketForm selectedTicket = ticketService.getTicketFormById(id);
//        Ticket selectedticket = ticketService.getTicketById(id);
        modelMap.addAttribute("photo", selectedTicket.getStringTicketPhoto());
        modelMap.addAllAttributes(Map.of(
                WebConstants.SELECTED_TICKET_MODEL, selectedTicket,
                WebConstants.COMMENT_MODEL, new Comment(),
                WebConstants.CURRENT_USER_MODEL, userService.getUserById(principal.getName())));
        return WebConstants.TICKET_DETAILS_VIEW;
    }

    @GetMapping(WebConstants.ALL_URL)
    public String getAllTickets(ModelMap modelMap, String keyword) {
        if (keyword != null) {
            modelMap.addAttribute(WebConstants.TICKETS_MODEL, ticketService.findByKeyword(keyword));
        } else {
            modelMap.addAttribute(WebConstants.TICKETS_MODEL, ticketService.getAllTickets());
        }
        return WebConstants.ALL_TICKETS_VIEW;
    }

    @GetMapping(WebConstants.ADD_URL)
    public String getNewTicketForm(ModelMap modelMap) {
        modelMap.addAllAttributes(Map.of(
                WebConstants.TICKET_FORM_MODEL, new TicketForm(),
                "ticketTypes", TicketType.allTypes(),
                WebConstants.USERS_MODEL, userService.getAllUsers()));
        return WebConstants.NEW_TICKET_VIEW;
    }

    @PostMapping(WebConstants.ADD_URL)
    public String addNewTicketForm(@Valid @ModelAttribute(WebConstants.TICKET_FORM_MODEL) final TicketForm ticketForm, final Errors errors, final RedirectAttributes redirectAttributes) throws IOException {
        if (errors.hasErrors()) {
            return WebConstants.NEW_TICKET_VIEW;
        }
        ticketService.createTicket(ticketForm);
        redirectAttributes.addFlashAttribute( "newTicketMsg", WebConstants.NEW_TICKET_MESSAGE);
        return "redirect:/";
    }

    @PostMapping("/{id}/comment/new")
    public String addNewCommentForm(@PathVariable Long id, Comment comment) {
        ticketService.createComment(id, comment);
        return "redirect:/tickets/{id}";
    }

    @PostMapping("/{id}/close")
    public String closeTicket(@PathVariable Long id, final RedirectAttributes redirectAttributes) {
        ticketService.closeTicket(id);
        redirectAttributes.addFlashAttribute( "tickedClosedMsg", WebConstants.TICKET_CLOSED_MESSAGE);
        return "redirect:/";
    }

    @GetMapping(value = "/")
    public String viewIndexPage() {
        log.info("Redirecting the index page to the controller method for fetching the employees in a "
                + "paginated fashion.");
        return "redirect:/tickets/page/1?sort-field=id&sort-dir=asc";
    }


    //TODO TicketSearchController - wyrzucic do osobnego Contr.
    // URL - http://localhost:10092/page/1?sort-field=firstName&sort-dir=desc

    @GetMapping(value = "/page/{page-number}")
    public String findPaginated(@PathVariable(name = "page-number") final int pageNo,
                                @RequestParam(name = "sort-field") final String sortField,
                                @RequestParam(name = "sort-dir") final String sortDir,
                                final Model model) {
        log.info("Getting the employees in a paginated way for page-number = {}, sort-field = {}, and "
                + "sort-direction = {}.", pageNo, sortField, sortDir);

        final int pageSize = 5;
        final Page<Ticket> page = ticketService.findPaginated(pageNo, pageSize, sortField, sortDir);
        final List<Ticket> listTickets = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listTickets", listTickets);
        return WebConstants.TICKETS_VIEW;
    }

    @PostMapping(value = "/{id}/upload")
    public String savePhoto(@ModelAttribute(WebConstants.SELECTED_TICKET_MODEL) TicketForm ticketForm, @PathVariable(name = "id") Long id) throws IOException {
        TicketForm ticketFormById = ticketService.getTicketFormById(id);
        ticketService.addPhoto(id, ticketForm.getTicketFormPhoto());
        return "redirect:/tickets/" + ticketFormById.getId().toString();
    }

}
