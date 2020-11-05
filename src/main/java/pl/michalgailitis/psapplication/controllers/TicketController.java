package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.michalgailitis.psapplication.domain.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.repository.TicketTypeRepository;
import pl.michalgailitis.psapplication.services.TicketService;
import pl.michalgailitis.psapplication.services.UserInfoService;
import pl.michalgailitis.psapplication.services.UserService;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserInfoService userInfoService;
    private final UserService userService;


    @GetMapping("/{id}")
    public String getTicketDetails(final ModelMap modelMap, @PathVariable final Long id) throws Exception {
        final Ticket selectedTicket = ticketService.getTicketById(id);
        modelMap.addAttribute("selectedticket", selectedTicket);
        return "ticketdetails";
    }

    @GetMapping("/all")
    public String getAllTickets(ModelMap modelMap){
        modelMap.addAttribute("tickets", ticketService.getAllTickets());
        return "alltickets";
    }

    @GetMapping()
    public String getNewTicketForm(ModelMap modelMap){
        modelMap.addAttribute("ticket", new Ticket());
        modelMap.addAttribute("tickettypes", ticketTypeRepository.getTicketTypeList());
        modelMap.addAttribute("users", userService.getAllUsers());
        return "newticket";
    }



    @PostMapping("/addticket")
    public String addNewTicketForm(Ticket ticket) throws Exception {
        ticket.setCreatedOn(LocalDate.now());
        ticket.setStatus(Status.OPEN);
        ticket.setAuthor(userService.getUserById(userInfoService.getCurrentUserName()));
        ticket.setResponsible(userService.getUserById(ticket.getResponsible().getEmail()));
        ticketService.createTicket(ticket);
        return "redirect:/";
    }

}
