package pl.michalgailitis.psapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.michalgailitis.psapplication.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
