package com.project.NeuroCards.service.impl;

import com.project.NeuroCards.entity.Flashcard;
import com.project.NeuroCards.entity.User;
import com.project.NeuroCards.repository.FlashcardRepository;
import com.project.NeuroCards.service.ProgressService;
import jakarta.servlet.http.HttpServletRequest;
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
    public Map<String, Integer> getProgress(Long deckId, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        List<Flashcard> cards = repository.findByDeckId(deckId)
                .stream()
                .filter(card -> card.getUser().getId().equals(user.getId()))
                .toList();

        int total = cards.size();
        int mastered = 0;
        int learning = 0;
        int due = 0;

        for (Flashcard card : cards) {

            if (card.getInterval() >= 24) {
                mastered++;
            } else {
                learning++;
            }

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