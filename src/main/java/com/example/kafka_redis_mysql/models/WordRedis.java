package com.example.kafka_redis_mysql.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("word")
@Data            
@NoArgsConstructor 
@AllArgsConstructor 
public class WordRedis {

    @Id
    private String id;
    private String word;
    private String description;
}
