package pl.michalgailitis.psapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotNull
    @Column(name = "proposed_solution")
    private String proposedSolution;

    @NotNull
    @Column(name = "ticket_type")
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @NotNull
    @Column(name = "created_on")
    private LocalDate createdOn;

    @NotNull
    @ManyToOne
    private User author;

    @NotNull
    @ManyToOne
    private User responsible;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public User getAuthor() {
        return author;
    }
}
