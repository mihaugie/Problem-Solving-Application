package pl.michalgailitis.psapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.michalgailitis.psapplication.model.ticket.specifications.Status;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tickets")
@Builder
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "proposed_solution")
    private String proposedSolution;

    @Column(name = "ticket_type")
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDate createdOn;

    @ManyToOne
    private User author;

    @ManyToOne
    private User responsible;

    @Lob
    @Column(name = "ticket_photo")

    private byte[] ticketPhoto;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;

    public Ticket(String title, String description, String proposedSolution, TicketType ticketType, LocalDate createdOn, User author, User responsible, Status status, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.proposedSolution = proposedSolution;
        this.ticketType = ticketType;
        this.createdOn = createdOn;
        this.author = author;
        this.responsible = responsible;
        this.status = status;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", proposedSolution='" + proposedSolution + '\'' +
                ", ticketType=" + ticketType +
                ", createdOn=" + createdOn +
                ", author=" + author +
                ", responsible=" + responsible +
                ", status=" + status +
                '}';
    }
}
