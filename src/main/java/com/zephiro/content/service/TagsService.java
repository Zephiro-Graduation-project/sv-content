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

    public Tags searchTagById(String id) {
        return tagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

    public void createTag(Tags tag) {
        tagsRepository.save(tag);
    }

    public void updateTag(String id, Tags tag) {
        if (tagsRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Tag not found with id: " + id);
        }

        Tags existingTag = searchTagById(id);
        existingTag.setName(tag.getName());
        tagsRepository.save(existingTag);
    }

    public void deleteTag(String id) {
        tagsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));

        if (!tagsRepository.findByTag(id).isEmpty()) {
            throw new RuntimeException("Tag is associated with content, cannot be deleted");
        }

        tagsRepository.deleteById(id);
    }

}
