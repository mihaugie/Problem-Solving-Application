package pl.michalgailitis.psapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michalgailitis.psapplication.model.user.specifications.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User {

    @Id
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name_surename")
    private String nameSurename;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    @JoinColumn(name = "author_email")
    @JsonIgnore
    private List<Ticket> ticketsAsAuthor;

    @OneToMany
    @JoinColumn(name = "responsible_email")
    @JsonIgnore
    private List<Ticket> ticketsAsResponsible;

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userNameSurename='" + nameSurename + '\'' +
                ", role=" + role +
                '}';
    }
}
