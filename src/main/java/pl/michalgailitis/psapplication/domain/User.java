package pl.michalgailitis.psapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "user_name_and_surename")
    private String userNameAndSurename;


    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    @JoinColumn(name="tickets_as_author")
    private List<Ticket> tickets_as_author;

    @OneToMany
    @JoinColumn(name="tickets_as_responsible")
    private List<Ticket> tickets_as_responsible;

}
