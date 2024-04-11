package com.irojas.demojwt.Posts_data;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.irojas.demojwt.User.User;
import com.irojas.demojwt.User.UserRepository;

@Service
public class PublicacionesService {
    @Autowired
    private PublicacionesRepository publicacionesRepository;

    @Autowired
    private UserRepository userRepository; // Inyecta el repositorio de usuarios

    public List<Publicaciones> getAllPublicacionesForUser(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return publicacionesRepository.findByUser(user.get()); 
        } else {
            return List.of(); 
        }
    }

    public List<Publicaciones> getAllPublicaciones() {
        List<Publicaciones> publicaciones = publicacionesRepository.findAll();
        for (Publicaciones publicacion : publicaciones) {
            User usuario = publicacion.getUser();
            usuario.getUsername();
        }
        return publicaciones;
    }


    public Optional<Publicaciones> getPublicacionById(Long id) {
        return publicacionesRepository.findById(id);
    }

    public Publicaciones createPublicacion(Publicaciones publicacion, Integer id) {

        Optional<User> user= userRepository.findById(id);
        if (user.isPresent()) {
            publicacion.setUser(user.get());
        }

        return publicacionesRepository.save(publicacion);
    }

    public Publicaciones updatePublicacion(Long id, Publicaciones publicacionDetails) {
        Publicaciones publicacion = publicacionesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        
        publicacion.setContenido(publicacionDetails.getContenido());
        publicacion.setImg_post(publicacionDetails.getImg_post());

        return publicacionesRepository.save(publicacion);
    }

    public void deletePublicacion(Long id) {
        publicacionesRepository.deleteById(id);
    }
}
