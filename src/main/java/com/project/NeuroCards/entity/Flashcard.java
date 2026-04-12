package com.project.NeuroCards.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;
    private String difficulty;
    private String lastReview;

    private int interval = 1;
    private int repetition = 0;
    private double easeFactor = 2.5;

    private Instant nextReviewTime = Instant.now();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;
}