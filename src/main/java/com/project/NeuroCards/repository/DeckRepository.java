package com.project.NeuroCards.repository;

import com.project.NeuroCards.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}