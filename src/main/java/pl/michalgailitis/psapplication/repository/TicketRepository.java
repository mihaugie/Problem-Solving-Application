package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.michalgailitis.psapplication.domain.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

//    @Query("SELECT s FROM shops s LEFT JOIN fetch s.stocks st WHERE st.product.id=:product_id")
//    List<Shop> findAllShopsWhichProductIsOnStock(@Param("product_id") Long productId);
//
//    //sposob na samodzielne zdefiniowanie zapytania a nie za pomocą generatora nazw zapytania
//    @Query("SELECT s FROM shops s WHERE s.name=:name AND s.address=:address")
//    Optional<Shop> findShopWhereNameAndAddressHaveValues(@Param("name") String name, @Param("address") String address);


    //napisać @Query z zapytaniem
//    @Query("SELECT t FROM tickets t WHERE t.author=:author")
//    List<Ticket> findTicketsForUserdashboard(@Param("author") User author, @Param("responsible") User responsible, @Param("status") Status status);

    @Query(value = "SELECT t FROM tickets t WHERE t.status=:status AND (t.author.email=:email OR t.responsible.email=:email)")
    Set<Ticket> find(@Param("status") Status status, @Param("email") String email);

    List<Ticket> findTicketsByAuthorOrResponsibleAndStatus(User author, User responsible, Status status);

    List<Ticket>findTicketsByTitle(String title);

    List<Ticket> findTicketsByAuthor(User authorId);

    List<Ticket> getAllByAuthor(User authorId);

}
