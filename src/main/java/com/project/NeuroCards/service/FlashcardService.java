package com.project.NeuroCards.service;

import com.project.NeuroCards.entity.Deck;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FlashcardService {

    Deck processPdf(MultipartFile file);

    List<Deck> getAllDecks();

    Deck getDeck(Long id);
}