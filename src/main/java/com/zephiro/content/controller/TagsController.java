package com.zephiro.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.content.entity.Tags;
import com.zephiro.content.service.TagsService;

@RestController
@RequestMapping("/content/admin/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllTags() {
        try {
            List<Tags> tags = tagsService.searchAllTags();
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching tags\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable String id) {
        try {
            Tags tags = tagsService.searchTagById(id);
            return ResponseEntity.ok(tags);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching tags\"}");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createTag(@RequestBody Tags tag) {
        try {
            tagsService.createTag(tag);
            return ResponseEntity.ok("{\"message\": \"Tag created successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateTag(@PathVariable String id, @RequestBody Tags tag) {
        try {
            tagsService.updateTag(id, tag);
            return ResponseEntity.ok("{\"message\": \"Tag updated successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable String id) {
        try {
            tagsService.deleteTag(id);
            return ResponseEntity.ok("{\"message\": \"Tag deleted successfully\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while deleting tag\"}");
        }
    }
}
