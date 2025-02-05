package com.example.kafka_redis_mysql.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.kafka_redis_mysql.models.WordRedis;

public interface WordRedisRepository extends CrudRepository<WordRedis, String> {}
