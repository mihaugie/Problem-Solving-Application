package pl.michalgailitis.psapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.michalgailitis.psapplication.model.Status;
import pl.michalgailitis.psapplication.model.TicketType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "description")
    private String description;

//    @NotNull
    @Column(name = "proposed_solution")
    private String proposedSolution;

    @NotNull
    @Column(name = "ticket_type")
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    //MB zamienic na adnotacje
    //@NotNull
    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDate createdOn;

    //@NotNull
    @ManyToOne
    private User author;

    //@NotNull
    @ManyToOne
    private User responsible;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;

    //MB usunąc nulle z konstruktora

    public Ticket(@NotNull String title, @NotNull String description, @NotNull String proposedSolution, @NotNull TicketType ticketType, @NotNull LocalDate createdOn, @NotNull User author, @NotNull User responsible, @NotNull Status status, List<Comment> comments) {
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
