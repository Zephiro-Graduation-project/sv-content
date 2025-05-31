package com.zephiro.content.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephiro.content.repository.ContentRepository;
import com.zephiro.content.repository.TagsRepository;
import com.zephiro.content.entity.Tag;
import com.zephiro.content.entity.Content;

@Service
public class UserService {
    
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagsRepository tagsRepository;
    
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

    public List<Content> suggestedContent(int stress, int anxiety) {
        Tag stressTag;
        Tag anxietyTag;

        if(stress >= 10 && stress <= 36) {
            stressTag = tagsRepository.findByName("Estrés bajo");
        } else if(stress >= 37 && stress <= 70) {
            stressTag = tagsRepository.findByName("Estrés moderado");
        } else if(stress >= 71 && stress <= 100) {
            stressTag = tagsRepository.findByName("Estrés alto");
        } else {
            throw new RuntimeException("Stress level out of range: " + stress);
        }

        if(anxiety >= 10 && anxiety <= 30) {
            anxietyTag = tagsRepository.findByName("Ansiedad mínima");
        } else if(anxiety >= 31 && anxiety <= 54) {
            anxietyTag = tagsRepository.findByName("Ansiedad leve");
        } else if(anxiety >= 55 && anxiety <= 76) {
            anxietyTag = tagsRepository.findByName("Ansiedad moderada");
        } else if(anxiety >= 77 && anxiety <= 100) {
            anxietyTag = tagsRepository.findByName("Ansiedad severa");
        } else {
            throw new RuntimeException("Anxiety level out of range: " + anxiety);
        }

        // Get content based on tags
        List<Content> basedOnStress = contentRepository.findByTag(stressTag.getId());
        List<Content> basedOnAnxiety = contentRepository.findByTag(anxietyTag.getId());

        // Create intercalated list
        List<Content> intercalatedList = new ArrayList<>();
        Iterator<Content> stressIterator = basedOnStress.iterator();
        Iterator<Content> anxietyIterator = basedOnAnxiety.iterator();

        // The first content in the list will be the one with a higher value
        if((stress*100/60) < (anxiety*100/41)) {
            while (stressIterator.hasNext() || anxietyIterator.hasNext()) {
                if (anxietyIterator.hasNext()) {
                    intercalatedList.add(anxietyIterator.next());
                }
                if (stressIterator.hasNext()) {
                    intercalatedList.add(stressIterator.next());
                }
            }
        } else {
            while (stressIterator.hasNext() || anxietyIterator.hasNext()) {
                if (stressIterator.hasNext()) {
                    intercalatedList.add(stressIterator.next());
                }
                if (anxietyIterator.hasNext()) {
                    intercalatedList.add(anxietyIterator.next());
                }
            }
        }

        return intercalatedList;
    }
}
