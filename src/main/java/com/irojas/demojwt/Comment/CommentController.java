package com.irojas.demojwt.Comment;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irojas.demojwt.Jwt.JwtService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.findCommentById(id);
        return comment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String token, @PathVariable Long postId) {
        try {
            String jwtToken = token.substring(7);
            Claims claims = jwtService.extractAllClaims(jwtToken);
            Integer userId = Integer.parseInt(claims.get("user", Map.class).get("id").toString());
            Comment newComment = commentService.saveComment(comment, userId, postId);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            Claims claims = jwtService.extractAllClaims(jwtToken);
            Integer userId = Integer.parseInt(claims.get("user", Map.class).get("id").toString());
            Comment updatedComment = commentService.updateComment(id, commentDetails, userId);
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            Claims claims = jwtService.extractAllClaims(jwtToken);
            Integer userId = Integer.parseInt(claims.get("user", Map.class).get("id").toString());
            boolean isDeleted = commentService.deleteCommentByUserAndPost(userId, commentId);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
