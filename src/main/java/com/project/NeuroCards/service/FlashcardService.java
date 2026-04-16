package com.project.NeuroCards.service;

import com.project.NeuroCards.entity.Deck;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FlashcardService {

    Deck processPdf(MultipartFile file, HttpServletRequest request);

    List<Deck> getAllDecks(HttpServletRequest request);

    Deck getDeck(Long id, HttpServletRequest request);
}