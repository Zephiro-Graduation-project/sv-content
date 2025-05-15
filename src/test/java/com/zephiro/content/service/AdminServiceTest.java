package com.zephiro.content.service;

import com.zephiro.content.entity.Content;
import com.zephiro.content.entity.Tag;
import com.zephiro.content.repository.ContentRepository;
import com.zephiro.content.repository.TagsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @AfterEach
    public void cleanup() {
        contentRepository.deleteAll();
        tagsRepository.deleteAll();
    }

    @Test
    public void searchAll_shouldReturnAllContents() {
        // Arrange
        Tag tag1 = tagsRepository.save(new Tag("Technology"));
        Tag tag2 = tagsRepository.save(new Tag("Science"));
        
        Content content1 = createTestContent("Content 1", "Description 1", "English", Arrays.asList(tag1));
        Content content2 = createTestContent("Content 2", "Description 2", "Spanish", Arrays.asList(tag2));
        
        contentRepository.save(content1);
        contentRepository.save(content2);

        // Act
        List<Content> contents = adminService.searchAll();

        // Assert
        Assertions.assertThat(contents).hasSize(2);
        Assertions.assertThat(contents)
            .extracting(Content::getName)
            .containsExactlyInAnyOrder("Content 1", "Content 2");
    }

    @Test
    public void searchById_shouldReturnSpecificContent() {
        // Arrange
        Tag tag = tagsRepository.save(new Tag("History"));
        Content content = createTestContent("History Content", "About ancient history", "English", Arrays.asList(tag));
        Content savedContent = contentRepository.save(content);

        // Act
        Content foundContent = adminService.searchById(savedContent.getId());

        // Assert
        Assertions.assertThat(foundContent).isNotNull();
        Assertions.assertThat(foundContent.getName()).isEqualTo("History Content");
    }

    @Test
    public void searchById_shouldThrowExceptionWhenNotFound() {
        // Act & Assert
        Assertions.assertThatThrownBy(() -> adminService.searchById("non-existent-id"))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Content not found with id");
    }

    @Test
    public void createContent_shouldSaveNewContent() {
        // Arrange
        Tag tag = tagsRepository.save(new Tag("Art"));
        Content content = createTestContent("Art Content", "About modern art", "English", Arrays.asList(tag));

        // Act
        adminService.createContent(content);

        // Assert
        List<Content> contents = contentRepository.findAll();
        Assertions.assertThat(contents).hasSize(1);
        Assertions.assertThat(contents.get(0).getName()).isEqualTo("Art Content");
    }

    @Test
    public void updateContent_shouldModifyExistingContent() {
        // Arrange
        Tag originalTag = tagsRepository.save(new Tag("Original"));
        Content originalContent = createTestContent("Original", "Original desc", "English", Arrays.asList(originalTag));
        Content savedContent = contentRepository.save(originalContent);

        Tag newTag = tagsRepository.save(new Tag("Updated"));
        Content updateData = createTestContent("Updated", "Updated desc", "Spanish", Arrays.asList(newTag));

        // Act
        adminService.updateContent(savedContent.getId(), updateData);

        // Assert
        Content updatedContent = contentRepository.findById(savedContent.getId()).get();
        Assertions.assertThat(updatedContent.getName()).isEqualTo("Updated");
        Assertions.assertThat(updatedContent.getDescription()).isEqualTo("Updated desc");
        Assertions.assertThat(updatedContent.getLanguage()).isEqualTo("Spanish");
        Assertions.assertThat(updatedContent.getTags())
            .extracting(Tag::getName)
            .containsExactly("Updated");
    }

    @Test
    public void updateContent_shouldThrowExceptionWhenNotFound() {
        // Arrange
        Tag tag = tagsRepository.save(new Tag("Test"));
        Content content = createTestContent("Test", "Test", "English", Arrays.asList(tag));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> adminService.updateContent("non-existent-id", content))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Content not found with id");
    }

    @Test
    public void deleteContent_shouldRemoveContent() {
        // Arrange
        Tag tag = tagsRepository.save(new Tag("ToDelete"));
        Content content = createTestContent("ToDelete", "Delete me", "English", Arrays.asList(tag));
        Content savedContent = contentRepository.save(content);

        // Act
        adminService.deleteContent(savedContent.getId());

        // Assert
        Assertions.assertThat(contentRepository.count()).isZero();
    }

    @Test
    public void searchByTag_shouldReturnFilteredContents() {
        // Arrange
        Tag techTag = tagsRepository.save(new Tag("Technology"));
        Tag scienceTag = tagsRepository.save(new Tag("Science"));
        
        Content content1 = createTestContent("Tech News", "Tech description", "English", Arrays.asList(techTag));
        Content content2 = createTestContent("Science News", "Science description", "English", Arrays.asList(scienceTag));
        Content content3 = createTestContent("Tech Review", "Another tech", "Spanish", Arrays.asList(techTag));
        
        contentRepository.save(content1);
        contentRepository.save(content2);
        contentRepository.save(content3);

        // Act
        List<Content> techContents = adminService.searchByTag(techTag.getId());

        // Assert
        Assertions.assertThat(techContents).hasSize(2);
        Assertions.assertThat(techContents)
            .extracting(Content::getName)
            .containsExactlyInAnyOrder("Tech News", "Tech Review");
    }

    private Content createTestContent(String name, String description, String language, List<Tag> tags) {
        return new Content(
            name,
            description,
            "Test Author",
            "Test Source",
            language,
            "https://test.com",
            "/images/test.png",
            tags
        );
    }
}