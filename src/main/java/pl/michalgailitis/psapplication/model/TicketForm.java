package pl.michalgailitis.psapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import pl.michalgailitis.psapplication.domain.User;
import pl.michalgailitis.psapplication.model.ticket.specifications.TicketType;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketForm {

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

    private MultipartFile ticketPhoto;
}
