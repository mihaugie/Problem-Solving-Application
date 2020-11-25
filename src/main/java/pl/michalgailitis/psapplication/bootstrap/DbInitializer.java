package pl.michalgailitis.psapplication.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.ticket.specifications.Status;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;
import pl.michalgailitis.psapplication.model.user.specifications.Role;
import pl.michalgailitis.psapplication.repository.CommentRepository;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

    //TODO dodac caly component do profilu dev - spring sczytuje nazwe profilu z nazwy pliku properties po "-"

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        passwordEncoder.encode("pass1");
        passwordEncoder.encode("pass2");
        passwordEncoder.encode("pass3");
        User user1 = createUser(TestConsts.EMAIL_1, TestConsts.PASSWORD_1, TestConsts.NAME_SURENAME_1, TestConsts.USER_ROLE);
        User user2 = createUser(TestConsts.EMAIL_2, TestConsts.PASSWORD_2, TestConsts.NAME_SURENAME_2, TestConsts.ADMIN_ROLE);
        User user3 = createUser(TestConsts.EMAIL_3, TestConsts.PASSWORD_3, TestConsts.NAME_SURENAME_3, TestConsts.USER_ROLE);
        Ticket ticket1 = createTicket(TestConsts.TICKET_TITLE_1, TestConsts.TICKET_DESC, TestConsts.TICKET_SOLUTION, TicketType.BUG, LocalDate.now(), user2, user1, Status.OPEN);
        Ticket ticket2 = createTicket(TestConsts.TICKET_TITLE_2, TestConsts.TICKET_DESC2, TestConsts.TICKET_SOLUTION, TicketType.IDEA, LocalDate.now(), user3, user2, Status.OPEN);
        Ticket ticket3 = createTicket(TestConsts.TICKET_TITLE_3, TestConsts.TICKET_DESC3, TestConsts.TICKET_SOLUTION, TicketType.PROBLEM, LocalDate.now(), user3, user1, Status.CLOSED);
        Ticket ticket4 = createTicket(TestConsts.TICKET_TITLE_4, TestConsts.TICKET_DESC3, TestConsts.TICKET_SOLUTION, TicketType.TODO, LocalDate.now(), user1, user2, Status.CLOSED);
        createComment(TestConsts.COMMENT_DESC, LocalDate.now(), user1, ticket1);
        createComment(TestConsts.COMMENT_DESC_2, LocalDate.now(), user1, ticket2);
        createComment(TestConsts.COMMENT_DESC_3, LocalDate.now(), user1, ticket2);
        createComment(TestConsts.COMMENT_DESC_4, LocalDate.now(), user2, ticket2);
        createComment(TestConsts.COMMENT_DESC_5, LocalDate.now(), user3, ticket3);
        createComment(TestConsts.COMMENT_DESC_6, LocalDate.now(), user3, ticket4);

        System.out.println("test");
    }

    private Ticket createTicket(String title, String description, String proposedSolution, TicketType ticketType, LocalDate createdOn, User author, User responsible, Status status) {
        Ticket newTicket = new Ticket(title, description, proposedSolution, ticketType, createdOn, author, responsible, status, List.of());
        return ticketRepository.save(newTicket);
    }

    private User createUser(String email, String password, String nameSurename, Role role) {
        User newUser = new User(email, password, nameSurename, role, List.of(), List.of());
        return userRepository.save(newUser);
    }

    private Comment createComment(String commentDesc, LocalDate createdOn, User author, Ticket ticket) {
        Comment newComment = new Comment(commentDesc, createdOn, author, ticket);
        return commentRepository.save(newComment);
    }
}