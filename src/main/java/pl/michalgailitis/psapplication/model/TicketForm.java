package pl.michalgailitis.psapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.ticket.specifications.Status;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketForm {

    //TODO dodac message properties i w zaleznosci od locale tlumaczyc messages
    //TODO dodac wiadomosc do notNulli

    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Length(min = 5, message = "Minimum lenght is 5")
    private String description;

    @NotNull
    private String proposedSolution;

    @NotNull
    private TicketType ticketType;

    @NotNull
    private User responsible;

    private List<Comment> comments;
    private Status status;
    private User author;
    private MultipartFile ticketFormPhoto;
    private String stringTicketPhoto;
}
