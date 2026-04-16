package com.project.NeuroCards.service;

import com.project.NeuroCards.entity.Flashcard;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReviewService {

    List<Flashcard> getReviewCards(Long deckId, HttpServletRequest request);

    Flashcard reviewCard(Long cardId, String rating, HttpServletRequest request);
}