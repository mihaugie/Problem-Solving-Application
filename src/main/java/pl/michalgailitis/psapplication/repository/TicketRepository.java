package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.michalgailitis.psapplication.model.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;

import java.util.List;
import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket, Long> {




    @Query(value = "SELECT t FROM tickets t WHERE t.status=:status AND (t.author.email=:email OR t.responsible.email=:email)")
    Set<Ticket> find(@Param("status") Status status, @Param("email") String email);

    List<Ticket> findTicketsByAuthorOrResponsibleAndStatus(User author, User responsible, Status status);

    List<Ticket>findTicketsByTitle(String title);

    List<Ticket> findTicketsByAuthor(User authorId);

    List<Ticket> getAllByAuthor(User authorId);

    //SORT:

    List<Ticket> findAllByOrderByStatusAsc();

}
