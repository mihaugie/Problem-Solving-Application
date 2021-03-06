package pl.michalgailitis.psapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
@Builder
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "comment_desc")
    private String commentDesc;

    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDate createdOn;

    @ManyToOne
    private User author;

    @ManyToOne
    @JsonIgnore
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
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

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comment(String commentDesc, LocalDate createdOn, User author, Ticket ticket) {
        this.commentDesc = commentDesc;
        this.createdOn = createdOn;
        this.author = author;
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentDesc='" + commentDesc + '\'' +
                ", createdOn=" + createdOn +
                ", author=" + author +
                '}';
    }
}