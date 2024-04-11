package com.irojas.demojwt.Posts_data;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.irojas.demojwt.User.User;


@Repository
public interface PublicacionesRepository extends JpaRepository<Publicaciones, Long> {

    List<Publicaciones> findByUser(User user);
    
    // @Query("SELECT p FROM Publicaciones p LEFT JOIN FETCH p.likes")
    // // List<Publicaciones> findPublicacionesWithLikes();
}
