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
import org.springframework.web.multipart.MultipartFile;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.model.TicketForm;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.mappers.TicketMapper;
import pl.michalgailitis.psapplication.services.users.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @GetMapping("/{id}")
    public String getTicketDetails(final ModelMap modelMap, @PathVariable final Long id, @AuthenticationPrincipal Principal principal) throws IOException {
        final TicketForm selectedTicket = ticketService.getTicketFormById(id);

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
    public String addNewTicketForm(@Valid @ModelAttribute("ticketForm") final TicketForm ticketForm, final Errors errors) throws IOException {
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
        return "ticketsView";
    }

    @PostMapping(value = "/{id}/upload")
    public String saveTicket(@ModelAttribute("selectedticket") TicketForm ticketForm, @PathVariable(name = "id") Long id) throws IOException {


        TicketForm ticketFormById = ticketService.getTicketFormById(id);
        ticketFormById.setTicketPhoto(ticketForm.getTicketPhoto());
        Ticket ticket = ticketMapper.createTicket(ticketFormById);


//        System.out.println(ticketFormById);
//        byte[] bytes = file.getBytes();
//        ticketById.setTicketPhoto(bytes);

//        uiModel.asMap().clear();
//        //process upload file
//        if(file != null) {
//            byte[] filecontent = null;
//            try
//            {
//                InputStream inputStream = file.getInputStream();
//                if(inputStream == null)
//                filecontent = IOUtils.toByteArray(inputStream);
//                ticket.setTicketPhoto(filecontent);
//            }catch(IOException ex) {
//            }
//            ticket.setTicketPhoto(filecontent);
//        }

        ticketRepository.save(ticket);
        return "redirect:/ticketdetails/" + ticket.getId().toString();
    }
}
