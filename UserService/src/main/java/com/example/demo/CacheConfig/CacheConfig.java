package com.example.demo.CacheConfig;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;


@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer{

	  @Bean
	  @Override
	    public CacheManager cacheManager() {
	        // Configure and return CacheManager instance
		  CaffeineCacheManager cacheManager = new CaffeineCacheManager("myCache");
	        cacheManager.setCaffeine(caffeineCacheBuilder());
	        return cacheManager;
	    }
	  
	  Caffeine<Object, Object> caffeineCacheBuilder() {
	        return Caffeine.newBuilder()
	                .expireAfterWrite(10, TimeUnit.MINUTES)
	                .maximumSize(100);
	    }

	    @Bean
	    @Override
	    public org.springframework.cache.interceptor.KeyGenerator keyGenerator() {
	        // Configure and return KeyGenerator instance
	        return new SimpleKeyGenerator();
	    }



	    @Bean
	    public CacheErrorHandler cacheErrorHandler() {
	        // Configure and return CacheErrorHandler instance
	        return new SimpleCacheErrorHandler();
	    }

}
