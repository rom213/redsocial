package com.irojas.demojwt.Likes;

import java.time.LocalDateTime;

import com.irojas.demojwt.Posts_data.Publicaciones;
import com.irojas.demojwt.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="likes")
public class Likes {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private LocalDateTime createdAt;  


    // private User user;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="post_id")
    private Publicaciones publicaciones;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    
}
