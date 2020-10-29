package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.repository.TicketRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) throws Exception {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("There is no ticked with %d", id)));
    }

    public Ticket createTicket(final Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(final Long id){
        ticketRepository.deleteById(id);
    }


}
