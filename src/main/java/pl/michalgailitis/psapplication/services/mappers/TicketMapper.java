package pl.michalgailitis.psapplication.services.mappers;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.TicketForm;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
public class TicketMapper {
    public Ticket createTicket(TicketForm ticketForm) throws IOException {
        return Ticket.builder()
                .title(ticketForm.getTitle())
                .description(ticketForm.getDescription())
                .proposedSolution(ticketForm.getProposedSolution())
                .ticketType(ticketForm.getTicketType())
                .responsible(ticketForm.getResponsible())
                .ticketPhoto(ticketForm.getTicketPhoto().getBytes())
                .build();
    }

    public TicketForm createTicketForm(Ticket ticket) throws IOException {
        return TicketForm.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .proposedSolution(ticket.getProposedSolution())
                .ticketType(ticket.getTicketType())
                .responsible(ticket.getResponsible())
                .ticketPhoto(null)
                .build();
    }


}
