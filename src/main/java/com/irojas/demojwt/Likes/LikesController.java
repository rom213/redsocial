package com.irojas.demojwt.Likes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irojas.demojwt.Jwt.JwtService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/v1/likes")
public class LikesController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private LikesService likesService;

    // @GetMapping
    // public ResponseEntity<List<Likes>> getAllLikes() {
    //     List<Likes> likes = likesService.findAllLikes();
    //     return new ResponseEntity<>(likes, HttpStatus.OK);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<List<Likes>> getLikesById(@PathVariable Long id) {
        List<Likes> likes = likesService.findLikeById(id);
        // if (likes.isEmpty()) {
        //     return new ResponseEntity<>([],HttpStatus.NOT_FOUND);
        // }
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }
    

    @PostMapping("/{id}")
    public ResponseEntity<?> createLike(@RequestBody Likes like, @RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            String jwtToken = token.substring(7);
            Claims claims = jwtService.extractAllClaims(jwtToken);
        
            String userId = claims.get("user", Map.class).get("id").toString();
            Likes newLike = likesService.saveLike(like, Integer.parseInt(userId), id);

            return new ResponseEntity<>(newLike, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("not found post",HttpStatus.NOT_FOUND);
        }
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Likes> updateLike(@PathVariable Long id, @RequestBody Likes likeDetails) {
    //     Likes updatedLike = likesService.updateLike(id, likeDetails);
    //     if (updatedLike == null) {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    //     return new ResponseEntity<>(updatedLike, HttpStatus.OK);
    // }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long postId, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Claims claims = jwtService.extractAllClaims(jwtToken);
        int userId = (int) claims.get("user", Map.class).get("id");
        boolean isDeleted = likesService.deleteLikeByUserAndPost(userId, postId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
