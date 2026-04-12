package com.project.NeuroCards.repository;

import com.project.NeuroCards.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByDeckId(Long deckId);
}