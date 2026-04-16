package com.project.NeuroCards.service.impl;

import com.project.NeuroCards.entity.Flashcard;
import com.project.NeuroCards.entity.User;
import com.project.NeuroCards.repository.FlashcardRepository;
import com.project.NeuroCards.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final FlashcardRepository repository;

    public ReviewServiceImpl(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Flashcard> getReviewCards(Long deckId, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        return repository.findByDeckId(deckId)
                .stream()
                .filter(card -> card.getUser().getId().equals(user.getId()))
                .filter(card -> card.getNextReviewTime() == null ||
                        card.getNextReviewTime().isBefore(Instant.now()))
                .toList();
    }

    @Override
    public Flashcard reviewCard(Long cardId, String rating, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        Flashcard card = repository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (!card.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        switch (rating.toLowerCase()) {

            case "again" -> {
                card.setRepetition(0);
                card.setInterval(1); // +1 hour
            }

            case "good" -> {
                card.setRepetition(card.getRepetition() + 1);
                card.setInterval(Math.max(1, card.getInterval() + 6)); // +6 hours
            }

            case "easy" -> {
                card.setRepetition(card.getRepetition() + 1);
                card.setInterval(Math.max(1, card.getInterval() + 24)); // +24 hours
            }

            default -> throw new RuntimeException("Invalid rating");
        }


        card.setLastReview(rating.toUpperCase());
        card.setNextReviewTime(
                Instant.now().plusSeconds(card.getInterval() * 3600)
        );
        return repository.save(card);
    }
}