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

//    @Override
//    public List<Flashcard> getReviewCards(Long deckId) {
//        return repository.findByDeckId(deckId)
//                .stream()
//                .filter(card -> card.getNextReviewTime().isBefore(Instant.now()))
//                .filter(card -> card.getNextReviewTime().isBefore(Instant.now()))
//                .toList();
//    }

    @Override
    public List<Flashcard> getReviewCards(Long deckId) {
        return repository.findByDeckId(deckId); // no filter
    }

    @Override
    public Flashcard reviewCard(Long cardId, String rating) {

        Flashcard card = repository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        switch (rating.toLowerCase()) {

            case "again" -> {
                card.setRepetition(0);
                card.setInterval(1);
                card.setLastReview("AGAIN");
            }

            case "good" -> {
                card.setRepetition(card.getRepetition() + 1);
                card.setInterval((int) (card.getInterval() * card.getEaseFactor()));
                card.setLastReview("GOOD");
            }

            case "easy" -> {
                card.setRepetition(card.getRepetition() + 1);
                card.setEaseFactor(card.getEaseFactor() + 0.15);
                card.setInterval((int) (card.getInterval() * card.getEaseFactor()));
                card.setLastReview("EASY");
            }

            default -> throw new RuntimeException("Invalid rating");
        }

        // NEXT REVIEW TIME (FIXED)
//        card.setNextReviewTime(Instant.now());

        card.setNextReviewTime(
                Instant.now().plusSeconds(card.getInterval() * 24 * 60 * 60)
        );

        return repository.save(card);
    }
}