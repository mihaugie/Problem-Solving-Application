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
    @Enumerated(EnumType.ORDINAL)
    private TicketType ticketType;

    @NotNull
    @Column(name = "created_on")
    private LocalDate createdOn;

    @NotNull
  //  @Column(name = "author")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @NotNull
 //   @Column(name = "responsible")
    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private User responsible;

//    @NotNull
//    @Column(name = "authorId")
//    private String authorId;
//
//    @NotNull
//    @Column(name = "authorNameAndSurename")
//    private String authorNameAndSurename;
//
//    @NotNull
//    @Column(name = "responsibleId")
//    private String responsibleId;
//
//    @NotNull
//    @Column(name = "responsibleNameAndSurename")
//    private String responsibleNameAndSurename;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
 //   @Column(name = "comment")
    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;



}
