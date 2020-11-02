package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findTicketsByAuthorOrResponsible(User author, User responsible);

    List<Ticket>findTicketsByTitle(String title);

    List<Ticket> findTicketsByAuthor(User authorId);

    List<Ticket> getAllByAuthor(User authorId);

}
