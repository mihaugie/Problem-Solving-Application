package pl.michalgailitis.psapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @Email
    @Column(name = "email")
    private String email;


    @NotNull
    @Column(name = "password")
    private String password;


    @NotNull
    @Column(name = "name_surename")
    private String nameSurename;


    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    @JoinColumn(name="author_email")
    @JsonIgnore
    private List<Ticket> tickets_as_author;

    @OneToMany
    @JoinColumn(name="responsible_email")
    @JsonIgnore
    private List<Ticket> tickets_as_responsible;

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
