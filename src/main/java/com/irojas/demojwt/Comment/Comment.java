package com.irojas.demojwt.Comment;

import java.time.LocalDateTime;

import com.irojas.demojwt.Posts_data.Publicaciones;
import com.irojas.demojwt.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private LocalDateTime createdAt;  

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // This ensures user_id is not nullable
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // This ensures user_id is not nullable
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Publicaciones publicaciones;

    private String contenido;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    
}
