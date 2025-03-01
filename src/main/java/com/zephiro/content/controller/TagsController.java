package com.zephiro.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.content.entity.Tags;
import com.zephiro.content.service.TagsService;

@RestController
@RequestMapping("/content/admin")
public class TagsController {

    @Autowired
    private TagsService tagsService;
    
    @GetMapping("/tags/all")
    public ResponseEntity<?> getAllTags() {
        try {
            List<Tags> tags = tagsService.searchAllTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching tags\"}");
        }
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<?> getTagById(@PathVariable Long id) {
        try {
            Tags tags = tagsService.searchTagById(id);
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching tags\"}");
        }
    }
}
