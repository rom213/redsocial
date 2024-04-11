package com.irojas.demojwt.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irojas.demojwt.Posts_data.PublicacionesRepository;
import com.irojas.demojwt.User.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublicacionesRepository postRepository;

    // public List<Comment> findAllCommennts() {
    // return commentRepository.findAll();
    // }

    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Optional<Comment> findCommentByIdPost(Long post_id) {
        return commentRepository.findById(post_id);
    }

    @Transactional
    public Comment saveComment(Comment comment, Integer userId, Long postId) {
        // Encuentra el usuario y la publicación por sus IDs
        comment.setUser(
                userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        comment.setPublicaciones(
                postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Publicación no encontrada")));

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long id_comment, Comment updatedComment, Integer user_id) {
        Comment comment = commentRepository.findById(id_comment)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!comment.getUser().getId().equals(user_id)) {
            throw new RuntimeException(
                    "Operación no permitida. El usuario no tiene permisos para actualizar este comentario.");
        }

        comment.setContenido(updatedComment.getContenido());

        return commentRepository.save(comment);
    }

    @Transactional
    public boolean deleteCommentByUserAndPost(Integer userId, Long comment_id) {
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException(
                    "Operación no permitida. El usuario no tiene permisos para actualizar este comentario.");
        }
        commentRepository.delete(comment);
        return true;
    }
}
