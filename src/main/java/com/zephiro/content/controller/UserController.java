package com.zephiro.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zephiro.content.entity.Content;
import com.zephiro.content.service.UserService;

@RestController
@RequestMapping("/content/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllContent() {
        try {
            List<Content> content = userService.searchAll();
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/bytag/{tag_id}")
    public ResponseEntity<?> getContentByTag(@PathVariable String tag_id) {
        try {
            List<Content> content = userService.searchByTag(tag_id);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContentById(@PathVariable String id) {
        try {
            Content content = userService.searchById(id);
            return ResponseEntity.ok(content);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }

    @GetMapping("/suggested/{stress}/{anxiety}")
    public ResponseEntity<?> getSuggestedContent(@PathVariable int stress, @PathVariable int anxiety) {
        try {
            List<Content> content = userService.suggestedContent(stress, anxiety);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error occurred while fetching content\"}");
        }
    }
}
