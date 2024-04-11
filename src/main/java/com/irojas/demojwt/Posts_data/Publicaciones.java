package com.irojas.demojwt.Posts_data;

import java.time.LocalDateTime;
import java.util.List;

import com.irojas.demojwt.Likes.Likes;
import com.irojas.demojwt.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="publicaciones")
public class Publicaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private LocalDateTime createdAt;  

    @ManyToOne(fetch = FetchType.EAGER, optional = false) // Esta línea es la nueva que agregamos
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // @OneToMany(mappedBy = "publicaciones", fetch = FetchType.EAGER)
    // private List<Likes> likes;

    private String contenido;
    private String img_post;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
