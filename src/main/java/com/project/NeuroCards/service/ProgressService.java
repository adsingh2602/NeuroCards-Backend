package com.project.NeuroCards.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface ProgressService {
    Map<String, Integer> getProgress(Long deckId, HttpServletRequest request);
}