package com.zephiro.content.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.content.repository.ContentRepository;
import com.zephiro.content.entity.Content;

@Service
public class ContentUserService {
    
    @Autowired
    private ContentRepository contentRepository;
    
    public List<Content> searchAll() {
        return contentRepository.findAll();
    }

    public Content searchById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + id));
    }
}
