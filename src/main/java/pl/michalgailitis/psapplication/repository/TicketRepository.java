package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.ticket.specifications.Status;

import java.util.List;
import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT t FROM tickets t WHERE t.status=:status AND (t.author.email=:email OR t.responsible.email=:email)")
    Set<Ticket> find(@Param("status") Status status, @Param("email") String email);

    @Query(value = "SELECT t FROM tickets t WHERE t.status=:status AND (t.author.email=:email OR t.responsible.email=:email) AND (t.description LIKE %:keyword% OR t.proposedSolution LIKE %:keyword%)")
    Set<Ticket> findForUserDashboard(@Param("status") Status status, @Param("email") String email, @Param("keyword") String keyword);

    List<Ticket> findTicketsByTitle(String title);

    List<Ticket> getAllByAuthor(User authorId);

    @Query(value = "SELECT * FROM TICKETS t WHERE t.description LIKE %:keyword% OR t.proposed_solution LIKE %:keyword%", nativeQuery = true)
    List<Ticket> findByKeyword(@Param("keyword") String keyword);

    //TODO SORT:
    List<Ticket> findAllByOrderByStatusAsc();

}
