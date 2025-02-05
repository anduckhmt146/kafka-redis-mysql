package com.example.kafka_redis_mysql.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kafka_redis_mysql.dtos.WordDto;
import com.example.kafka_redis_mysql.models.WordMySql;
import com.example.kafka_redis_mysql.models.WordRedis;
import com.example.kafka_redis_mysql.repositories.WordMySqlRepository;
import com.example.kafka_redis_mysql.repositories.WordRedisRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {

    @Autowired
    private WordRedisRepository wordRedisRepository;

    @Autowired
    private WordMySqlRepository wordMySqlRepository;

    // Create or update a word in both Redis and MySQL
    public WordDto saveWord(WordDto wordDto) {
        // Save to Redis
        WordRedis wordRedis = new WordRedis(wordDto.getId(), wordDto.getWord(), wordDto.getDescription());
        WordRedis savedWordRedis = wordRedisRepository.save(wordRedis);

        // Save to MySQL
        WordMySql wordMySql = new WordMySql(
            null,  
            wordDto.getWord(),
            wordDto.getDescription()
        );
        wordMySqlRepository.save(wordMySql);

        // Return a WordDto containing both Redis and MySQL information
        return new WordDto(
            savedWordRedis.getId(),
            savedWordRedis.getWord(),
            savedWordRedis.getDescription()
        );
    }

    // Get word by ID from Redis, if not found in Redis, check MySQL
    public WordDto getWordById(String id) {
        // First, check Redis
        WordRedis redisWord = wordRedisRepository.findById(id).orElse(null);

        if (redisWord != null) {
            return new WordDto(redisWord.getId(), redisWord.getWord(), redisWord.getDescription());
        }

        // If not found in Redis, check MySQL
        WordMySql mysqlWord = wordMySqlRepository.findById(Long.valueOf(id)).orElse(null);

        if (mysqlWord != null) {
            return new WordDto(mysqlWord.getId().toString(), mysqlWord.getWord(), mysqlWord.getDescription());
        }

        // Return null if word is not found in either
        return null;
    }

    // Get all words (check Redis first, then MySQL)
    public List<WordDto> getAllWords() {
        Iterable<WordRedis> redisWords = wordRedisRepository.findAll();

        List<WordDto> result = new ArrayList<>();

        // If Redis has words, return them
        redisWords.forEach(redisWord -> result.add(new WordDto(redisWord.getId(), redisWord.getWord(), redisWord.getDescription())));

        // If no words in Redis, check MySQL
        if (result.isEmpty()) {
            List<WordMySql> mysqlWords = wordMySqlRepository.findAll();
            for (WordMySql mysqlWord : mysqlWords) {
                result.add(new WordDto(mysqlWord.getId().toString(), mysqlWord.getWord(), mysqlWord.getDescription()));
            }
        }

        return result;
    }

    // Search for a word in MySQL (prefix search)
    public List<WordDto> searchByWord(WordDto wordDto) {
        List<WordMySql> mysqlResults = wordMySqlRepository.findByWordPrefix(wordDto.getWord());
        List<WordDto> result = new ArrayList<>();

        for (WordMySql mysqlWord : mysqlResults) {
            result.add(new WordDto(mysqlWord.getId().toString(), mysqlWord.getWord(), mysqlWord.getDescription()));
        }

        return result;
    }
}
