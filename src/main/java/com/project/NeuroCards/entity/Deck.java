package com.project.NeuroCards.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Instant createdAt = Instant.now();

    @JsonManagedReference
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Flashcard> flashcards;

    private Instant lastStudiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}