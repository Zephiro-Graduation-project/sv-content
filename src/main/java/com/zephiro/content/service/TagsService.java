package com.zephiro.content.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.content.repository.TagsRepository;
import com.zephiro.content.entity.Tag;

@Service
public class TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    public List<Tag> searchAllTags() {
        return tagsRepository.findAll();
    }

    public Tag searchTagById(String id) {
        return tagsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

    public void createTag(Tag tag) {
        tagsRepository.save(tag);
    }

    public void updateTag(String id, Tag tag) {
        if (tagsRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Tag not found with id: " + id);
        }

        Tag existingTag = searchTagById(id);
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
