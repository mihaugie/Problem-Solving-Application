package pl.michalgailitis.psapplication.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.Ticket;
import pl.michalgailitis.psapplication.services.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketService ticketService;

    @GetMapping
    public List<Ticket> getTickets(){
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable(name = "id") Long id) throws Exception {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable(name = "id") Long id){
        ticketService.deleteTicket(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable(name = "id") Long id) throws Exception {
        return ticketService.getComments(id);
    }

    @PutMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket addComment(@PathVariable(name = "id") Long id, @RequestBody Comment newComment){

        return ticketService.createComment(id, newComment);
    }

    //póki co nie potrzeny tutaj ticket_id - poprawnosc tej logiki usuwania komentarza do weryfikacji po stworzeniu frontu
    @DeleteMapping("{ticket_id}/comments/{comment_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(name = "ticket_id") Long ticket_id, @PathVariable(name = "comment_id") Long comment_id){
        ticketService.deleteComment(ticket_id, comment_id);
    }

}
