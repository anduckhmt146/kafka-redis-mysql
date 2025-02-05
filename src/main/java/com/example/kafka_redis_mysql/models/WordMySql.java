package com.example.kafka_redis_mysql.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  // MySQL entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordMySql {

    @Id  // MySQL Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    private Long id;

    private String word;
    private String description;
}
