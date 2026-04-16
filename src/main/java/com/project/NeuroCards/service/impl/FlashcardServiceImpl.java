package com.project.NeuroCards.service.impl;

import com.project.NeuroCards.entity.Deck;
import com.project.NeuroCards.entity.Flashcard;
import com.project.NeuroCards.entity.User;
import com.project.NeuroCards.repository.DeckRepository;
import com.project.NeuroCards.repository.FlashcardRepository;
import com.project.NeuroCards.service.AiService;
import com.project.NeuroCards.service.FlashcardService;
import com.project.NeuroCards.util.PdfUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    private final DeckRepository deckRepository;
    private final FlashcardRepository flashcardRepository;
    private final AiService aiService;

    public FlashcardServiceImpl(DeckRepository deckRepository,
                                FlashcardRepository flashcardRepository,
                                AiService aiService) {
        this.deckRepository = deckRepository;
        this.flashcardRepository = flashcardRepository;
        this.aiService = aiService;
    }

    @Override
    public Deck processPdf(MultipartFile file, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        String text = PdfUtil.extractText(file);
        List<String[]> generatedCards = aiService.generateFlashcards(text);

        Deck deck = new Deck();
        deck.setName(file.getOriginalFilename());
        deck.setUser(user);
        deck = deckRepository.save(deck);

        List<Flashcard> cards = new ArrayList<>();

        for (String[] qa : generatedCards) {

            Flashcard card = new Flashcard();
            card.setQuestion(qa[0]);
            card.setAnswer(qa[1]);

            int length = qa[1].length();

            if (length < 80) card.setDifficulty("EASY");
            else if (length < 150) card.setDifficulty("MEDIUM");
            else card.setDifficulty("HARD");

            card.setDeck(deck);
            card.setUser(user);

            cards.add(card);
        }

        flashcardRepository.saveAll(cards);
        deck.setFlashcards(cards);

        return deck;
    }

    @Override
    public List<Deck> getAllDecks(HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        return deckRepository.findByUser(user);
    }

    @Override
    public Deck getDeck(Long id, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deck not found"));

        if (!deck.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return deck;
    }
}