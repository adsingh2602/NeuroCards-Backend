package com.project.NeuroCards.repository;

import com.project.NeuroCards.entity.Deck;
import com.project.NeuroCards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findByUser(User user);
}