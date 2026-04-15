package com.project.NeuroCards.controller;

import com.project.NeuroCards.entity.Deck;
import com.project.NeuroCards.repository.DeckRepository;
import com.project.NeuroCards.service.FlashcardService;
import com.project.NeuroCards.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/api")
public class FlashcardController {

    private final FlashcardService service;
    private final DeckRepository deckRepository;
    private final ProgressService progressService;

    public FlashcardController(FlashcardService service,
                               DeckRepository deckRepository,
                               ProgressService progressService) {
        this.service = service;
        this.deckRepository = deckRepository;
        this.progressService = progressService;
    }

    @PostMapping(value = "/upload-pdf", consumes = "multipart/form-data")
    public ResponseEntity<Deck> uploadPdf(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.processPdf(file));
    }

    @GetMapping("/decks")
    public ResponseEntity<List<Deck>> getDecks() {
        return ResponseEntity.ok(service.getAllDecks());
    }

    @GetMapping("/deck/{id}")
    public ResponseEntity<Deck> getDeck(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDeck(id));
    }

    @DeleteMapping("/decks/{id}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long id) {
        deckRepository.deleteById(id);
        return ResponseEntity.ok("Deck deleted successfully");
    }

    @GetMapping("/decks/{id}/progress")
    public ResponseEntity<Map<String, Integer>> getProgress(@PathVariable Long id) {
        return ResponseEntity.ok(progressService.getProgress(id));
    }
}