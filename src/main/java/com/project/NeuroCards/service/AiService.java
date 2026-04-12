package com.project.NeuroCards.service;

import java.util.List;

public interface AiService {
    List<String[]> generateFlashcards(String text);
}