package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.michalgailitis.psapplication.domain.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
