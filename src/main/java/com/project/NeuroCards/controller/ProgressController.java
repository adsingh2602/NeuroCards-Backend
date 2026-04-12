package com.project.NeuroCards.controller;

import com.project.NeuroCards.service.ProgressService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProgressController {

    private final ProgressService service;

    public ProgressController(ProgressService service) {
        this.service = service;
    }

    @GetMapping("/progress/{deckId}")
    public Map<String, Integer> getProgress(@PathVariable Long deckId) {
        return service.getProgress(deckId);
    }
}