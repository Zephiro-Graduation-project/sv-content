package com.zephiro.content.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "content")
public class Content {

    @Id
    private String id;
    private String name;
    private String description;
    private String author;
    private String source; // This would be the organization that uploaded the content
    private String language;
    private String url;
    private String imagePath;

    @DBRef
    private List<Tag> tags;

    
    public Content(String name, String description, String author, String source, String language, String url, String imagePath, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.source = source;
        this.language = language;
        this.url = url;
        this.imagePath = imagePath;
        this.tags = tags;
    }

    public Content() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
