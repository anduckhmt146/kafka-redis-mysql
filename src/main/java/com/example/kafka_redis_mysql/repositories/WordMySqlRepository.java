package com.example.kafka_redis_mysql.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.kafka_redis_mysql.models.WordMySql;

public interface WordMySqlRepository extends JpaRepository<WordMySql, Long> {
    
    @Query("SELECT w FROM WordMySql w WHERE w.word LIKE %?1%")
    List<WordMySql> findByWordPrefix(String prefix);
}
