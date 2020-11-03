package pl.michalgailitis.psapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.repository.TicketRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ticketdetails/{id}")
public class TicketController {

    private final TicketRepository ticketRepository;


    @GetMapping
    public String getTicketDetails(final ModelMap modelMap, @PathVariable final Long id){

        final Ticket selectedTicket = ticketRepository.getOne(id);
        modelMap.addAttribute("selectedticket", selectedTicket);

        return "ticketdetails";
    }

}
