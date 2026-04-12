package com.project.NeuroCards.dto;

public record FlashcardDTO(
        String question,
        String answer,
        String difficulty
) {}