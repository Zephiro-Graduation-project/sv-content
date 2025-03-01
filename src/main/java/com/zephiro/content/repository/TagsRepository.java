package com.zephiro.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zephiro.content.entity.Content;
import com.zephiro.content.entity.Tags;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {

    @Query("SELECT c FROM Content c JOIN c.tags t WHERE t.id = :tag_id")
    List<Content> findByTag(Long tag_id);
}