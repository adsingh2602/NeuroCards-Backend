package com.project.NeuroCards.service;

import java.util.Map;

public interface ProgressService {
    Map<String, Integer> getProgress(Long deckId);
}