package pl.michalgailitis.psapplication.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.michalgailitis.psapplication.domain.*;
import pl.michalgailitis.psapplication.repository.CommentRepository;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

//@Profile("dev")
@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;


    //MB wyrzucić stringi do stałych
    //MB przerobić wiersze na metody

    @Override
    public void run(String... args) throws Exception {

        //TAK MA BYC
        User user1 = createUser(DbConsts.EMAIL_1, DbConsts.PASSWORD_1, DbConsts.nameSurename1, DbConsts.userRole);

//        User user1 = new User(DbConsts.EMAIL_1, DbConsts.PASSWORD_1, DbConsts.nameSurename1, DbConsts.userRole, List.of(), List.of());
        User user2 = new User(DbConsts.EMAIL_2, DbConsts.PASSWORD_2, DbConsts.nameSurename2, DbConsts.adminRole, List.of(), List.of());
        User user3 = new User(DbConsts.EMAIL_3, DbConsts.PASSWORD_3, DbConsts.nameSurename3, DbConsts.userRole, List.of(), List.of());
        final Ticket ticket1 = new Ticket(DbConsts.ticketTitle1, DbConsts.ticketDesc, DbConsts.ticketSolution, TicketType.BUG, LocalDate.now(), user2, user1, Status.OPEN, List.of());
        final Ticket ticket2 = new Ticket(DbConsts.ticketTitle2, DbConsts.ticketDesc,  DbConsts.ticketSolution, TicketType.IDEA, LocalDate.now(), user2, user2, Status.OPEN, List.of());
        final Ticket ticket3 = new Ticket(DbConsts.ticketTitle3, DbConsts.ticketDesc,  DbConsts.ticketSolution, TicketType.PROBLEM, LocalDate.now(), user2, user1, Status.CLOSED, List.of());
        final Ticket ticket4 = new Ticket(DbConsts.ticketTitle4, DbConsts.ticketDesc,  DbConsts.ticketSolution, TicketType.TODO, LocalDate.now(), user1, user2, Status.CLOSED, List.of());
        Comment comment1 = new Comment(DbConsts.commentDesc, LocalDate.now(), user1, ticket1);
        Comment comment21 = new Comment(DbConsts.commentDesc2, LocalDate.now(), user1, ticket2);
        Comment comment22 = new Comment(DbConsts.commentDesc3, LocalDate.now(), user1, ticket2);
        Comment comment23 = new Comment(DbConsts.commentDesc4, LocalDate.now(), user2, ticket2);
        Comment comment24 = new Comment(DbConsts.commentDesc5, LocalDate.now(), user3, ticket3);
        Comment comment25 = new Comment(DbConsts.commentDesc6, LocalDate.now(), user3, ticket4);


//        createUser(userRepository, DbConsts.email2, DbConsts.password2, DbConsts.nameSurename2, DbConsts.adminRole);
//        createUser(userRepository, DbConsts.email3, DbConsts.password3, DbConsts.nameSurename3, DbConsts.userRole);



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

//    private void createTicket(JpaRepository jpaRepository, String title, String description, String proposedSolution, TicketType ticketType, LocalDate createdOn, User author, User responsible, Status status){
//        Ticket newTicket = new Ticket(title, description, proposedSolution, ticketType, createdOn, author, responsible, status, List.of())
//        jpaRepository.save(newTicket);
//    }
//
//
    private User createUser(String email, String password, String nameSurename, Role role){
        User newUser = new User(email, password, nameSurename, role, List.of(), List.of());
        return userRepository.save(newUser);
    }
//
//    private void createComment(JpaRepository jpaRepository, String commentDesc, LocalDate createdOn, User author, Ticket ticket){
//        Comment newComment = new Comment(commentDesc, createdOn, author, ticket);
//        jpaRepository.save(newComment);
//    }

}
