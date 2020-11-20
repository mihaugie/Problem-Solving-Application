package pl.michalgailitis.psapplication.services.tickets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.mailing.EmailSender;
import pl.michalgailitis.psapplication.model.TicketForm;
import pl.michalgailitis.psapplication.model.ticket.specifications.Status;
import pl.michalgailitis.psapplication.repository.TicketRepository;
import pl.michalgailitis.psapplication.repository.UserRepository;
import pl.michalgailitis.psapplication.services.CommentService;
import pl.michalgailitis.psapplication.services.MailingMessages;
import pl.michalgailitis.psapplication.services.tickets.TicketMapper;
import pl.michalgailitis.psapplication.services.users.UserInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final UserInfoService userInfoService;
    private final EmailSender emailSender;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    //TODO dodać wyjątki  własnego typu
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("There is no ticked with %d", id)));
    }

//    public Set<Ticket> getTicketForUserDashboard(final Status status, final String email) {
//        return ticketRepository.find(status, email);
//    }

    public Set<Ticket> getTicketForUserDashboard(final Status status, final String email, final String keyword) {
        return ticketRepository.findForUserDashboard(status, email, keyword);
    }

    public List<Ticket> getTicketByAuthor(final User authorId) {
        return ticketRepository.getAllByAuthor(authorId);
    }

    public List<Ticket> getTicketByTitle(final String title) {
        return ticketRepository.findTicketsByTitle(title);
    }

    public Ticket createTicket(final TicketForm ticketForm) {
        Ticket cretedTicket = ticketMapper.createTicket(ticketForm);
        cretedTicket.setStatus(Status.OPEN);
        cretedTicket.setAuthor(userRepository.findByEmail(userInfoService.getCurrentUserId()));
        cretedTicket.setResponsible(userRepository.findByEmail(ticketForm.getResponsible().getEmail()));
        final String addresseeOfNewTicketEmail = ticketForm.getResponsible().getEmail();
        emailSender.sendMail(addresseeOfNewTicketEmail, MailingMessages.EMAIL_MESSAGE_TICKET_CREATED_SUBJECT, MailingMessages.EMAIL_MESSAGE_TICKET_CREATED_BODY);
        return ticketRepository.save(cretedTicket);
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
        emailSender.sendMail(addresseeOfNewCommentEmail, MailingMessages.EMAIL_MESSAGE_COMMENT_CREATED_SUBJECT, MailingMessages.EMAIL_MESSAGE_COMMENT_CREATED_BODY);
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

    public Page<Ticket> findPaginated(final int pageNumber, final int pageSize,
                                      final String sortField, final String sortDirection) {
        log.info("Fetching the paginated tickets from the dB.");
        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return ticketRepository.findAll(pageable);
    }

    public List<Ticket> findByKeyword(String keyword){
        return ticketRepository.findByKeyword(keyword);
    }


    //TODO MB: sortowanie
//        ticketRepository.findAll(Sort.by("proposedSolution").ascending());
}
