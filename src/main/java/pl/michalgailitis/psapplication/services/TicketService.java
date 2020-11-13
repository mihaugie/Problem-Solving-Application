package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.mailing.EmailSenderImpl;
import pl.michalgailitis.psapplication.model.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.repository.CommentRepository;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.repository.UserRepository;
import pl.michalgailitis.psapplication.services.users.UserInfoService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final UserInfoService userInfoService;
    private final EmailSenderImpl emailSender;

    //DONE: MB Zamienic exception na RunTimeException
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    //dodać wyjątki  własnego typu
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("There is no ticked with %d", id)));
    }

    public Set<Ticket> getTicketForUserDashboard(final Status status, final String email) {
        return ticketRepository.find(status, email);
    }
//
//    public List<Ticket> getTicketByAuthorOrResponsibleAndStatus(final User author, final User responsible, final Status status) {
//        return ticketRepository.findTicketsByAuthorOrResponsibleAndStatus(author, responsible, status);
//    }

    public List<Ticket> getTicketByAuthor(final User authorId) {
        return ticketRepository.getAllByAuthor(authorId);
    }

    public List<Ticket> getTicketByTitle(final String title) {

        return ticketRepository.findTicketsByTitle(title);

    }

    //MB @Auth Principal zamiast User Info Service
    //MB - mapowanie wyrzucić do serwisu - lub do model - klasa TicketForm (Ticket.builder(). ... ) , to samo z User
    // services/mapper/TicketMapper i ten mapper wstrzykiwany jest TicketService do mapowania TicketForm na Ticket

    public Ticket createTicket(final Ticket ticket) {

        ticket.setStatus(Status.OPEN);
        ticket.setAuthor(userRepository.findByEmail(userInfoService.getCurrentUserId()));
        ticket.setResponsible(userRepository.findByEmail(ticket.getResponsible().getEmail()));

        final String addresseeOfNewTicketEmail = ticket.getResponsible().getEmail();
        emailSender.sendMail(addresseeOfNewTicketEmail, ServiceConsts.EMAIL_MESSAGE_TICKET_CREATED_SUBJECT, ServiceConsts.EMAIL_MESSAGE_TICKET_CREATED_BODY);

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(final Long id) {
        ticketRepository.deleteById(id);
    }


    public List<Comment> getComments(Long id) {
        return ticketRepository.findById(id).orElseThrow().getComments();

    }

    public Ticket createComment(final Long id, final Comment newComment) {
        newComment.setAuthor(userRepository.findByEmail(userInfoService.getCurrentUserId()));
        Ticket ticketToAddComment = ticketRepository.findById(id).orElseThrow();
        ticketToAddComment.getComments().add(newComment);
        String addresseeOfNewCommentEmail = ticketToAddComment.getResponsible().getEmail();
        emailSender.sendMail(addresseeOfNewCommentEmail, ServiceConsts.EMAIL_MESSAGE_COMMENT_CREATED_SUBJECT, ServiceConsts.EMAIL_MESSAGE_COMMENT_CREATED_BODY);

        return ticketRepository.save(ticketToAddComment);
    }

    public void deleteComment(final Long ticketId, final Long commentId) {
/*
        ticketRepository.findById(ticketId).orElseThrow().getComments().stream().forEach(p -> System.out.println(p.getId()));
        System.out.println(ticketRepository.findById(ticketId).orElseThrow().getComments().remove(commentId));
        ticketRepository.findById(ticketId).orElseThrow().getComments().stream().forEach(p -> System.out.println(p.getId()));
         System.out.println(ticketRepository.findById(ticketId).orElseThrow().getComments().remove(commentId));
*/
        commentService.deleteComment(commentId);

    }

    public Ticket closeTicket(final Long id) {

        Ticket ticketToClose = ticketRepository.findById(id).orElseThrow();
        ticketToClose.setStatus(Status.CLOSED);

        return ticketRepository.save(ticketToClose);
    }

    //MB: sortowanie
//        ticketRepository.findAll(Sort.by("proposedSolution").ascending());

}
