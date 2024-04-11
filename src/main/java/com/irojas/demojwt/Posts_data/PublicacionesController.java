package com.irojas.demojwt.Posts_data;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irojas.demojwt.Jwt.JwtService;
// import com.irojas.demojwt.User.User;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("api/v1/publicaciones")
public class PublicacionesController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PublicacionesService publicacionesService;

    @GetMapping
    public ResponseEntity<List<Publicaciones>> getAllPublicaciones() {
        List<Publicaciones> publicaciones = publicacionesService.getAllPublicaciones();
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    
    @GetMapping("/all/{id}")
    public ResponseEntity<List<Publicaciones>> getAllPublicacionesForUser(@PathVariable Integer id) {
        List<Publicaciones> publicaciones = publicacionesService.getAllPublicacionesForUser(id);
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicaciones> getPublicacionById(@PathVariable Long id) {
        Optional<Publicaciones> publicacion = publicacionesService.getPublicacionById(id);
        return publicacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createPublicacion(@RequestBody Publicaciones publicacion, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Claims claims = jwtService.extractAllClaims(jwtToken);


        String userId = claims.get("user", Map.class).get("id").toString();
        Publicaciones nuevaPublicacion = publicacionesService.createPublicacion(publicacion, Integer.parseInt(userId));
        return new ResponseEntity<>(nuevaPublicacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicaciones> updatePublicacion(@PathVariable Long id, @RequestBody Publicaciones publicacionDetails) {
        Publicaciones updatedPublicacion = publicacionesService.updatePublicacion(id, publicacionDetails);
        return new ResponseEntity<>(updatedPublicacion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        publicacionesService.deletePublicacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
