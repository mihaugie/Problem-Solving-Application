package pl.michalgailitis.psapplication.services.tickets;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.TicketForm;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
@Transactional
public class TicketMapper {
    public Ticket createTicket(TicketForm ticketForm) {
        return Ticket.builder()
                .title(ticketForm.getTitle())
                .description(ticketForm.getDescription())
                .proposedSolution(ticketForm.getProposedSolution())
                .ticketType(ticketForm.getTicketType())
                .responsible(ticketForm.getResponsible())
                .build();
    }
}
