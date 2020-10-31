package pl.michalgailitis.psapplication.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.michalgailitis.psapplication.domain.*;
import pl.michalgailitis.psapplication.repository.CommentRepository;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;


    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("user@email.com", "dupa", "Michał Gailitis", Role.USER, List.of(), List.of());
        User user2 = new User("user2@email.com", "cycki", "Adam Małysz", Role.ADMIN, List.of(), List.of());
        User user3 = new User("user3@email.com", "tola", "Iza Bela", Role.USER, List.of(), List.of());

        final Ticket ticket1 = new Ticket(null, "Ticket title 1", "Desc 1", "Solution 1", TicketType.BUG, LocalDate.now(), user2, user1, Status.OPEN, List.of());
        final Ticket ticket2 = new Ticket(null, "Ticket title 2", "Desc 2", "Solution 2", TicketType.IDEA, LocalDate.now(), user2, user2, Status.OPEN, List.of());
        final Ticket ticket3 = new Ticket(null, "Ticket title 3", "Desc 3", "Solution 3", TicketType.PROBLEM, LocalDate.now(), user2, user1, Status.CLOSED, List.of());
        final Ticket ticket4 = new Ticket(null, "Ticket title 4", "Desc 4", "Solution 4", TicketType.TODO, LocalDate.now(), user1, user2, Status.OPEN, List.of());
        Comment comment1 = new Comment(null, "Comment desc 1", LocalDate.now(), user1, ticket1);
        Comment comment21 = new Comment(null, "Ticket2: Comment desc 21", LocalDate.now(), user1, ticket2);
        Comment comment22 = new Comment(null, "Ticket2: Comment desc 22", LocalDate.now(), user1, ticket2);
        Comment comment23 = new Comment(null, "Ticket2: Comment desc 23", LocalDate.now(), user2, ticket2);
        Comment comment24 = new Comment(null, "Ticket3: Comment desc 24", LocalDate.now(), user3, ticket3);
        Comment comment25 = new Comment(null, "Ticket4: Comment desc 25", LocalDate.now(), user3, ticket4);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        ticketRepository.save(ticket4);
        commentRepository.save(comment1);
        commentRepository.save(comment21);
        commentRepository.save(comment22);
        commentRepository.save(comment23);
        commentRepository.save(comment24);
        commentRepository.save(comment25);

    }
}
