package com.project.NeuroCards.controller;

import com.project.NeuroCards.entity.Flashcard;
import com.project.NeuroCards.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    // 🎯 GET REVIEW SESSION
    @GetMapping("/review-session/{deckId}")
    public List<Flashcard> getReviewCards(@PathVariable Long deckId) {
        return service.getReviewCards(deckId);
    }

    // 🔁 REVIEW CARD
    @PostMapping("/review/{cardId}")
    public Flashcard reviewCard(
            @PathVariable Long cardId,
            @RequestParam String rating) {
        return service.reviewCard(cardId, rating);
    }
}