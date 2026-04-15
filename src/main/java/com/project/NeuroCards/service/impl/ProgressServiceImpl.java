package com.project.NeuroCards.service.impl;

import com.project.NeuroCards.entity.Flashcard;
import com.project.NeuroCards.repository.FlashcardRepository;
import com.project.NeuroCards.service.ProgressService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final FlashcardRepository repository;

    public ProgressServiceImpl(FlashcardRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, Integer> getProgress(Long deckId) {

        List<Flashcard> cards = repository.findByDeckId(deckId);

        int total = cards.size();
        int mastered = 0;
        int learning = 0;
        int due = 0;

        for (Flashcard card : cards) {

            // MASTERED LOGIC
            if (card.getEaseFactor() > 2.5 && card.getRepetition() > 3) {
                mastered++;
            } else {
                learning++;
            }

            // DUE LOGIC (safe check)
            if (card.getNextReviewTime() != null &&
                    card.getNextReviewTime().isBefore(Instant.now())) {
                due++;
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("total", total);
        result.put("mastered", mastered);
        result.put("learning", learning);
        result.put("due", due);

        return result;
    }
}