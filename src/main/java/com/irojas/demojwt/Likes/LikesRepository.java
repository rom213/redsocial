package com.irojas.demojwt.Likes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>  {
    Optional<Likes> findByUserIdAndPublicacionesId(Integer userId, Long publicacionId);
    List<Likes> findByPublicacionesId(Long publicacionId);
}
