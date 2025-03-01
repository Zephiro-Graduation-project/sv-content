package com.zephiro.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zephiro.content.entity.Tags;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {

}