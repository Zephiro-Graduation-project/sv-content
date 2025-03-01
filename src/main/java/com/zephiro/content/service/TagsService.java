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

    public void createTag(Tags tag) {
        try {
            tagsRepository.save(tag);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during creation", e);
        }
    }

    public void updateTag(Long id, Tags tag) {
        try {
            Tags existingTag = searchTagById(id);
            existingTag.setName(tag.getName());
            tagsRepository.save(existingTag);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during update", e);
        }
    }

    public void deleteTag(Long id) {
        tagsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));

        if (!tagsRepository.findByTag(id).isEmpty()) {
            throw new RuntimeException("Tag is associated with content, cannot be deleted");
        }

        tagsRepository.deleteById(id);
    }

}
