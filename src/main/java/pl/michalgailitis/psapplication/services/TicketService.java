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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

//MB Zamienic exception na RunTimeException
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    //dodać wyjątki  własnego typu
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("There is no ticked with %d", id)));
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


    public List<Comment> getComments(Long id) {
        return ticketRepository.findById(id).orElseThrow().getComments();

    }

    public Ticket createComment (final Long id, final Comment newComment){
        Optional<Ticket> ticketToAddComment = ticketRepository.findById(id);
        ticketToAddComment.orElseThrow().getComments().add(newComment);
        Ticket ticket = ticketToAddComment.get();
        return ticketRepository.save(ticket);
    }

    public void deleteComment(final Long ticketId, final Long commentId){
//        ticketRepository.findById(ticketId).orElseThrow().getComments().stream().forEach(p -> System.out.println(p.getId()));
//        System.out.println(ticketRepository.findById(ticketId).orElseThrow().getComments().remove(commentId));
//        ticketRepository.findById(ticketId).orElseThrow().getComments().stream().forEach(p -> System.out.println(p.getId()));
//         System.out.println(ticketRepository.findById(ticketId).orElseThrow().getComments().remove(commentId));

        commentService.deleteComment(commentId);

    }

    public Ticket closeTicket(final Ticket ticket){
        return ticketRepository.save(ticket);
    }

}
