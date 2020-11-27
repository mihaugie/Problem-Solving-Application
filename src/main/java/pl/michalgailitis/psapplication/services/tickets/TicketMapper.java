package pl.michalgailitis.psapplication.services.tickets;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.model.TicketForm;

import java.io.IOException;
import java.util.Base64;

@Component
//@RequiredArgsConstructor
public class TicketMapper {
    public Ticket createTicket(TicketForm ticketForm) throws IOException {
        return Ticket.builder()
                .title(ticketForm.getTitle())
                .description(ticketForm.getDescription())
                .proposedSolution(ticketForm.getProposedSolution())
                .ticketType(ticketForm.getTicketType())
                .responsible(ticketForm.getResponsible())
//                .ticketPhoto(ticketForm.getTicketFormPhoto().getBytes())
                .build();
    }

    public TicketForm createTicketForm(Ticket ticket) throws IOException { TicketForm buildTicketForm = TicketForm.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .proposedSolution(ticket.getProposedSolution())
                .ticketType(ticket.getTicketType())
                .responsible(ticket.getResponsible())
                .status(ticket.getStatus())
                .author(ticket.getAuthor())
                .comments(ticket.getComments())
                .build();

        if (ticket.getTicketPhoto() != null) {
            buildTicketForm.setStringTicketPhoto(Base64.getEncoder().encodeToString(ticket.getTicketPhoto()));
        }
        return buildTicketForm;
    }
}
