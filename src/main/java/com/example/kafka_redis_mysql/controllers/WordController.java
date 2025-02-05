package com.example.kafka_redis_mysql.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.kafka_redis_mysql.dtos.WordDto;
import com.example.kafka_redis_mysql.services.WordService;

import java.util.List;

@RestController
@RequestMapping("/api/words")
public class WordController {

    @Autowired
    private WordService wordService;

    // Create or update a word in both Redis and MySQL
    @PostMapping
    public WordDto createWord(@RequestBody WordDto wordDto) {
        return wordService.saveWord(wordDto);
    }

    // Search for a word (can be enhanced for better search functionality)
    @GetMapping
    public List<WordDto> searchWords(@RequestParam(value = "word", required = false) String word) {
        if (word != null && !word.isEmpty()) {
            return wordService.searchByWord(new WordDto(null, word, null));
        }
        // If no word parameter is provided, get all words
        return wordService.getAllWords();
    }

    // Get all words from Redis and MySQL
    @GetMapping("/all")
    public List<WordDto> getAllWords() {
        return wordService.getAllWords();
    }

    // Get word by ID
    @GetMapping("/{id}")
    public WordDto getWordById(@PathVariable String id) {
        return wordService.getWordById(id);
    }
}
