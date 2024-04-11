package com.irojas.demojwt.Comment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // @Query("SELECT c FROM Comment c WHERE c.user.id = :userId AND c.publicaciones.id = :publicacionesId")
    // Optional<Comment> findCommentByUserIdAndCommentId(Integer userId, Long CommentId);
}
