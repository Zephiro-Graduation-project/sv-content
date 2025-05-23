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

    public List<Content> searchByTag(String tag_id) {
        return contentRepository.findByTag(tag_id);
    }

    public Content searchById(String id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + id));
    }

    public void createContent(Content content) {
        contentRepository.save(content);
    }

    public void updateContent(String id, Content content) {
        if (contentRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Content not found with id: " + id);
        }

        Content existingContent = searchById(id);
        existingContent.setName(content.getName());
        existingContent.setDescription(content.getDescription());
        existingContent.setAuthor(content.getAuthor());
        existingContent.setSource(content.getSource());
        existingContent.setLanguage(content.getLanguage());
        existingContent.setUrl(content.getUrl());
        existingContent.setImagePath(content.getImagePath());
        existingContent.setTags(content.getTags());
        contentRepository.save(existingContent);
    }

    public void deleteContent(String id) {
        if(searchById(id) != null)
            contentRepository.deleteById(id);
    }
}
