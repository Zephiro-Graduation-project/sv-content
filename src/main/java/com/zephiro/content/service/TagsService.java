package com.zephiro.content.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.content.repository.TagsRepository;
import com.zephiro.content.entity.Tags;

@Service
public class TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    public List<Tags> searchAllTags() {
        return tagsRepository.findAll();
    }

    public Tags searchTagById(Long id) {
        return tagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

}
