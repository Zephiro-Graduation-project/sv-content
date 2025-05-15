package com.zephiro.content.repository;

import com.zephiro.content.entity.Content;
import com.zephiro.content.entity.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ContentRepositoryTest {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagsRepository tagRepository;

    @AfterEach
    public void cleanup() {
        contentRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    public void saveContent_shouldReturnSavedContent() {
        // Arrange
        Tag savedTag1 = tagRepository.save(new Tag("Tag test 1"));
        Tag savedTag2 = tagRepository.save(new Tag("Tag test 2"));
        List<Tag> tags = Arrays.asList(savedTag1, savedTag2);
        
        Content content = new Content(
            "Content Title",
            "Content Description",
            "Alejandro Suarez",
            "Javeriana",
            "Spanish",
            "https://example.com",
            "/images/example.png",
            tags
        );

        // Act
        Content savedContent = contentRepository.save(content);

        // Assert
        Assertions.assertThat(savedTag1).isNotNull();
        Assertions.assertThat(savedTag2).isNotNull();
        Assertions.assertThat(tags).hasSize(2);

        Assertions.assertThat(savedContent).isNotNull();
        Assertions.assertThat(savedContent.getName()).isEqualTo("Content Title");
    }

    @Test
    public void findById_shouldReturnContent() {
        // Arrange
        Tag savedTag1 = tagRepository.save(new Tag("Tag test 1"));
        Tag savedTag2 = tagRepository.save(new Tag("Tag test 2"));
        List<Tag> tags = Arrays.asList(savedTag1, savedTag2);
        
        Content content = new Content(
            "Content Title",
            "Content Description",
            "Alejandro Suarez",
            "Javeriana",
            "Spanish",
            "https://example.com",
            "/images/example.png",
            tags
        );
        Content savedContent = contentRepository.save(content);

        // Act
        Optional<Content> foundContent = contentRepository.findById(savedContent.getId());

        // Assert
        Assertions.assertThat(foundContent).isPresent();
        Assertions.assertThat(foundContent.get().getName()).isEqualTo("Content Title");
    }

    @Test
    public void findAll_shouldReturnAllContents() {
        // Arrange
        Tag savedTag1 = tagRepository.save(new Tag("Tag test 1"));
        Tag savedTag2 = tagRepository.save(new Tag("Tag test 2"));
        List<Tag> tags = Arrays.asList(savedTag1, savedTag2);
        
        Content content1 = new Content(
            "Content Title 1",
            "Content Description 1",
            "Alejandro Suarez",
            "Javeriana",
            "Spanish",
            "https://example.com",
            "/images/example.png",
            tags
        );
        
        Content content2 = new Content(
            "Content Title 2",
            "Content Description 2",
            "Alejandro Suarez",
            "Javeriana",
            "English",
            "https://example.com",
            "/images/example.png",
            tags
        );

        contentRepository.save(content1);
        contentRepository.save(content2);

        // Act
        List<Content> contents = contentRepository.findAll();

        // Assert
        Assertions.assertThat(contents).hasSize(2);
        Assertions.assertThat(contents)
            .extracting(Content::getName)
            .containsExactlyInAnyOrder("Content Title 1", "Content Title 2");
    }

    @Test
    public void updateContent_shouldModifyExistingContent() {
        // Arrange
        Tag savedTag1 = tagRepository.save(new Tag("Tag test 1"));
        Tag savedTag2 = tagRepository.save(new Tag("Tag test 2"));
        List<Tag> tags = Arrays.asList(savedTag1, savedTag2);
        
        Content content = new Content(
            "Content Title",
            "Content Description",
            "Alejandro Suarez",
            "Javeriana",
            "Spanish",
            "https://example.com",
            "/images/example.png",
            tags
        );
        Content savedContent = contentRepository.save(content);

        // Act
        savedContent.setDescription("Updated guide to Spring Boot");
        Content updatedContent = contentRepository.save(savedContent);

        // Assert
        Assertions.assertThat(updatedContent.getDescription()).isEqualTo("Updated guide to Spring Boot");
    }

    @Test
    public void deleteContent_shouldRemoveFromDatabase() {
        // Arrange
        Tag savedTag1 = tagRepository.save(new Tag("Tag test 1"));
        Tag savedTag2 = tagRepository.save(new Tag("Tag test 2"));
        List<Tag> tags = Arrays.asList(savedTag1, savedTag2);
        
        Content content = new Content(
            "Content Title",
            "Content Description",
            "Alejandro Suarez",
            "Javeriana",
            "Spanish",
            "https://example.com",
            "/images/example.png",
            tags
        );
        Content savedContent = contentRepository.save(content);

        // Act
        contentRepository.deleteById(savedContent.getId());
        Optional<Content> deletedContent = contentRepository.findById(savedContent.getId());

        // Assert
        Assertions.assertThat(deletedContent).isEmpty();
    }
} 