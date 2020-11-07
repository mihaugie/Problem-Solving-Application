package pl.michalgailitis.psapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "comment_desc")
    private String comment_desc;

    @NotNull
    @Column(name = "created_on")
    private LocalDate created_on;

    @NotNull
    @ManyToOne
    private User author;

    @ManyToOne
    @JsonIgnore
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public String getComment_desc() {
        return comment_desc;
    }

    public LocalDate getCreatedOn() {
        return created_on;
    }

    public User getAuthor() {
        return author;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setComment_desc(String comment_desc) {
        this.comment_desc = comment_desc;
    }

    public void setCreated_on(LocalDate created_on) {
        this.created_on = created_on;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentDesc='" + comment_desc + '\'' +
                ", createdOn=" + created_on +
                ", author=" + author +
                '}';
    }


}
