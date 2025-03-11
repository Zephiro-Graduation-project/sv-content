package com.zephiro.content.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.zephiro.content.entity.Content;

@Repository
public interface ContentRepository extends MongoRepository<Content, String> {
    
    @Query("{ 'tags.id': ?0 }")
    List<Content> findByTag(String tagId);

}
