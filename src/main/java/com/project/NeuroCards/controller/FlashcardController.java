package com.project.NeuroCards.controller;

import com.project.NeuroCards.entity.Deck;
import com.project.NeuroCards.repository.DeckRepository;
import com.project.NeuroCards.service.FlashcardService;
import com.project.NeuroCards.service.ProgressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:8090")
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
    public ResponseEntity<Deck> uploadPdf(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        if (file.getSize() > 100 * 1024 * 1024) {
            throw new RuntimeException("File too large (max 100MB)");
        }
        return ResponseEntity.ok(service.processPdf(file, request));
    }

    @GetMapping("/decks")
    public ResponseEntity<List<Deck>> getDecks(HttpServletRequest request) {
        return ResponseEntity.ok(service.getAllDecks(request));
    }

    @GetMapping("/deck/{id}")
    public ResponseEntity<Deck> getDeck(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(service.getDeck(id, request));
    }

    @DeleteMapping("/decks/{id}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long id) {
        deckRepository.deleteById(id);
        return ResponseEntity.ok("Deck deleted successfully");
    }

    @GetMapping("/decks/{id}/progress")
    public ResponseEntity<Map<String, Integer>> getProgress(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(progressService.getProgress(id, request));
    }
}