package com.example.kafka_redis_mysql.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {
    private String id;
    private String word;
    private String description;
}
