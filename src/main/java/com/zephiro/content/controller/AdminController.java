package com.zephiro.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.content.entity.Content;
import com.zephiro.content.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/content/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllContent() {
        try {
            List<Content> content = adminService.searchAll();
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContentById(@PathVariable Long id) {
        try {
            Content content = adminService.searchById(id);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createContent(@RequestBody Content content) {
        try {
            adminService.createContent(content);
            return ResponseEntity.ok("{\"message\": \"Content created successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while creating content\"}");
        }
        
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateContent(@PathVariable Long id, @RequestBody Content content) {
        try {
            Content existingContent = adminService.searchById(id);
            existingContent.setName(content.getName());
            existingContent.setDescription(content.getDescription());
            existingContent.setAuthor(content.getAuthor());
            existingContent.setSource(content.getSource());
            existingContent.setTags(content.getTags());
            adminService.updateContent(existingContent);
            return ResponseEntity.ok("{\"message\": \"Content updated successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while updating content\"}");
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable Long id) {
        try {
            adminService.deleteContent(id);
            return ResponseEntity.ok("{\"message\": \"Content deleted successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while deleting content\"}");
        }
    }
}
