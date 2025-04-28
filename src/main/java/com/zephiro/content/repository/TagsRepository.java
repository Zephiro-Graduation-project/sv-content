package com.zephiro.content.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.zephiro.content.entity.Content;
import com.zephiro.content.entity.Tag;

@Repository
public interface TagsRepository extends MongoRepository<Tag, String> {

    @Query("{ 'tags.id': ?0 }")
    List<Content> findByTag(String tagId);
    Tag findByName(String name);
}