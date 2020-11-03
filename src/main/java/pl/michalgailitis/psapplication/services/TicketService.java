package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Status;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.repository.CommentRepository;
import pl.michalgailitis.psapplication.repository.TicketRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CommentService commentService;


    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) throws Exception {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("There is no ticked with %d", id)));
    }

    public List<Ticket> getTicketByAuthorOrResponsibleAndStatus(final User author, final User responsible, final Status status) {
        return ticketRepository.findTicketsByAuthorOrResponsibleAndStatus(author, responsible, status);
    }

    public List<Ticket> getTicketByAuthor(final User authorId) {
        return ticketRepository.getAllByAuthor(authorId);
    }

    public List<Ticket> getTicketByTitle(final String title){
        return ticketRepository.findTicketsByTitle(title);

    }
    public Ticket createTicket(final Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(final Long id){
        ticketRepository.deleteById(id);
    }


    public List<Comment> getComments(Long id) throws Exception {
        return ticketRepository.findById(id).orElseThrow().getComments();

    }

    public boolean createComment (final Long id, final Comment newComment){
        return ticketRepository.findById(id).orElseThrow().getComments().add(newComment);
    }

    public void deleteComment(final Long ticketId, final Long commentId){
//        ticketRepository.findById(ticketId).orElseThrow().getComments().stream().forEach(p -> System.out.println(p.getId()));
//        System.out.println(ticketRepository.findById(ticketId).orElseThrow().getComments().remove(commentId));
//        ticketRepository.findById(ticketId).orElseThrow().getComments().stream().forEach(p -> System.out.println(p.getId()));
//         System.out.println(ticketRepository.findById(ticketId).orElseThrow().getComments().remove(commentId));

        commentService.deleteComment(commentId);

    }

}
