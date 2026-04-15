package com.project.NeuroCards.service.impl;

import com.project.NeuroCards.entity.Flashcard;
import com.project.NeuroCards.repository.FlashcardRepository;
import com.project.NeuroCards.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final FlashcardRepository repository;

    public ReviewServiceImpl(FlashcardRepository repository) {
        this.repository = repository;
    }

    //  Get ONLY due cards (spaced repetition logic)
    @Override
    public List<Flashcard> getReviewCards(Long deckId) {
        return repository.findByDeckId(deckId)
                .stream()
                .filter(card -> card.getNextReviewTime() == null ||
                        card.getNextReviewTime().isBefore(Instant.now()))
                .toList();
    }

    // Review logic (SM-2 inspired)
    @Override
    public Flashcard reviewCard(Long cardId, String rating) {

        Flashcard card = repository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        switch (rating.toLowerCase()) {

            case "again" -> {
                card.setRepetition(0);
                card.setInterval(1);
                card.setEaseFactor(Math.max(1.3,
                        card.getEaseFactor() - 0.2));
            }

            case "good" -> {
                card.setRepetition(card.getRepetition() + 1);

                int newInterval = (int) (card.getInterval() * card.getEaseFactor());
                card.setInterval(Math.max(1, newInterval));
            }

            case "easy" -> {
                card.setRepetition(card.getRepetition() + 1);
                card.setEaseFactor(card.getEaseFactor() + 0.15);

                int newInterval = (int) (card.getInterval() * card.getEaseFactor());
                card.setInterval(Math.max(1, newInterval));
            }

            default -> throw new RuntimeException("Invalid rating");
        }

        card.setLastReview(rating.toUpperCase());

        card.getDeck().setLastStudiedAt(Instant.now());

        card.setNextReviewTime(
                Instant.now().plusSeconds(card.getInterval() * 86400)
        );

        return repository.save(card);
    }
}