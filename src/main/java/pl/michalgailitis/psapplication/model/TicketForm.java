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
    private Long id;

    @NotNull(message = "Please provide title")
    private String title;

    @NotNull(message = "Please provide description of an issue")
    @Length(min = 5, message = "Minimum lenght is 5")
    private String description;

    @NotNull(message = "Please provide proposed solution")
    private String proposedSolution;

    private TicketType ticketType;
    private User responsible;
    private List<Comment> comments;
    private Status status;
    private User author;
    private MultipartFile ticketFormPhoto;
    private String stringTicketPhoto;
}
