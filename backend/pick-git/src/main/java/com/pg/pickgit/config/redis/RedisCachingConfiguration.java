package com.pg.pickgit.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.persistence.CollectionTable;
import java.time.Duration;
import java.util.ArrayList;

@EnableCaching
@Configuration
@Profile("!test")
public class RedisCachingConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;
    private final ObjectMapper objectMapper;

    public RedisCachingConfiguration(
            RedisConnectionFactory redisConnectionFactory,
            ObjectMapper objectMapper
    ){
        this.redisConnectionFactory = redisConnectionFactory;
        this.objectMapper = objectMapper;
    }

    @Bean(name = "cacheManager")
    public Cachemanager redisCacheManager(){
        CollectionTable collectionTable = objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, PostResponseDto.class);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new Jackson2JsonRedisSerializer<>(
                                        collectionType
                                )
                        )
                )
                .entryTtl(Duration.ofMinutes(30));
        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnetcionFactory)
                .cacheDefaults(redisCachingConfiguration)
                .build();
    }
}
