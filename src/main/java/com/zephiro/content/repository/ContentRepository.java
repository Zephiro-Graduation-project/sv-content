package com.zephiro.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zephiro.content.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    
}
