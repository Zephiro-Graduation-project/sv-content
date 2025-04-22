package com.zephiro.content.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import com.zephiro.content.repository.TagsRepository;


@Controller
@Profile("test")
public class dbinit implements ApplicationRunner{

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if the database is empty
        if (tagsRepository.count() > 0) {
            return;
        } else {
            // Crear tags de prueba
            tagsRepository.save(new Tag("Estrés bajo"));
            tagsRepository.save(new Tag("Estrés moderado"));
            tagsRepository.save(new Tag("Estrés alto"));
            tagsRepository.save(new Tag("Ansiedad mínima"));
            tagsRepository.save(new Tag("Ansiedad leve"));
            tagsRepository.save(new Tag("Ansiedad moderada"));
            tagsRepository.save(new Tag("Ansiedad severa"));
        }
    }
}
