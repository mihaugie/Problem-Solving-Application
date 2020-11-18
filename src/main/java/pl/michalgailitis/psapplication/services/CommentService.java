package pl.michalgailitis.psapplication.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michalgailitis.psapplication.domain.Comment;
import pl.michalgailitis.psapplication.repository.CommentRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;


    public Comment createComment(final Comment Comment) {
        return commentRepository.save(Comment);
    }

    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }
}
