package com.project.NeuroCards.service;

import com.project.NeuroCards.entity.Flashcard;

import java.util.List;

public interface ReviewService {

    List<Flashcard> getReviewCards(Long deckId);

    Flashcard reviewCard(Long cardId, String rating);
}