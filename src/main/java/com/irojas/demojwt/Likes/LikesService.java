package com.irojas.demojwt.Likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irojas.demojwt.Posts_data.PublicacionesRepository;
import com.irojas.demojwt.User.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    // Suponiendo la existencia de UserService y PostService
    private final UserRepository userRepository;
    private final PublicacionesRepository postRepository;

    public LikesService(LikesRepository likesRepository, UserRepository userRepository, PublicacionesRepository postRepository) {
        this.likesRepository = likesRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<Likes> findAllLikes() {
        return likesRepository.findAll();
    }



    public List<Likes> findLikeById(Long id) {
        return likesRepository.findByPublicacionesId(id);
    }

    @Transactional
    public Likes saveLike(Likes like, Integer userId, Long postId) {
        // Encuentra el usuario y la publicación por sus IDs
        like.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        like.setPublicaciones(postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Publicación no encontrada")));
        
        return likesRepository.save(like);
    }

    @Transactional
    public Likes updateLike(Long id, Likes likeDetails) {
        return likesRepository.findById(id).map(like -> {
            like.setPublicaciones(likeDetails.getPublicaciones()); // Suponiendo que este es un campo válido
            // Aquí deberías actualizar los campos adicionales
            return likesRepository.save(like);
        }).orElseThrow(() -> new RuntimeException("Like no encontrado"));
    }

    @Transactional
    public boolean deleteLikeByUserAndPost(Integer userId, Long postId) {
        Optional<Likes> likeOpt = likesRepository.findByUserIdAndPublicacionesId(userId, postId);
        if (likeOpt.isPresent()) {
            likesRepository.delete(likeOpt.get());
            return true;
        }
        return false;
    }
}
