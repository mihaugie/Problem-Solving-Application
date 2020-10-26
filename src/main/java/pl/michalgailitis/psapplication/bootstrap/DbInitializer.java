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
        User user1 = new User("user@email.com", "dupa", "Michał Gailitis", Role.USER, null);
        User user2 = new User("user2@email.com", "cycki", "Adam Małysz", Role.ADMIN, null);

        final Ticket ticket1 = new Ticket(1L, "Ticket title 1", "Desc 1", "Solution 1", TicketType.BUG, LocalDate.now(), user2, user1, Status.OPEN, List.of());
        Comment comment1 = new Comment(1L, "Comment desc 1", LocalDate.now(), user1, ticket1);

        userRepository.save(user1);
        userRepository.save(user2);
        ticketRepository.save(ticket1);
        commentRepository.save(comment1);


    }
}
