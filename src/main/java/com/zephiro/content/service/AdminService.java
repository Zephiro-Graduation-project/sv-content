package com.zephiro.content.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.content.repository.ContentRepository;
import com.zephiro.content.entity.Content;

@Service
public class AdminService {
    
    @Autowired
    private ContentRepository contentRepository;
    
    public List<Content> searchAll() {
        return contentRepository.findAll();
    }

    public List<Content> searchByTag(Long tag_id) {
        return contentRepository.findByTag(tag_id);
    }

    public Content searchById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + id));
    }

    public void createContent(Content content) {
        try {
            contentRepository.save(content);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during creation", e);
        }
    }

    public void updateContent(Long id, Content content) {
        try {
            Content existingContent = searchById(id);
            existingContent.setName(content.getName());
            existingContent.setDescription(content.getDescription());
            existingContent.setAuthor(content.getAuthor());
            existingContent.setSource(content.getSource());
            existingContent.setTags(content.getTags());
            contentRepository.save(existingContent);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during update", e);
        }
    }

    public void deleteContent(Long id) {
        try {
            if(searchById(id) != null)
                contentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during deletion", e);
        }
    }
}
