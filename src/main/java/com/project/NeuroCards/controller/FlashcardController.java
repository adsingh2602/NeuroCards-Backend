package com.project.NeuroCards.controller;

import com.project.NeuroCards.entity.Deck;
import com.project.NeuroCards.service.FlashcardService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/api")
public class FlashcardController {

    private final FlashcardService service;

    public FlashcardController(FlashcardService service) {
        this.service = service;
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
}