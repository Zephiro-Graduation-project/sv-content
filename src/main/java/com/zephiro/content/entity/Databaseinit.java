package com.zephiro.content.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import com.zephiro.content.repository.ContentRepository;
import com.zephiro.content.repository.TagsRepository;


@Controller
@Profile("dev")
public class Databaseinit implements ApplicationRunner{

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Tags tag1 = tagsRepository.save(new Tags("Etiqueta 1"));
        Tags tag2 = tagsRepository.save(new Tags("Etiqueta 2"));
        Tags tag3 = tagsRepository.save(new Tags("Etiqueta 3"));
        Tags tag4 = tagsRepository.save(new Tags("Etiqueta 4"));
        Tags tag5 = tagsRepository.save(new Tags("Etiqueta 5"));
        Tags tag6 = tagsRepository.save(new Tags("Etiqueta 6"));

        Content content1 = new Content(
            "Contenido A", 
            "Descripción A", 
            "Autor A", 
            "Fuente A", 
            "Español", 
            "https://example.com/a", 
            "https://example.com/images/a.jpg", 
            List.of(tag1, tag2)
        );
        contentRepository.save(content1);

        Content content2 = new Content(
            "Contenido B", 
            "Descripción B", 
            "Autor B", 
            "Fuente B", 
            "Inglés", 
            "https://example.com/b", 
            "https://example.com/images/b.jpg", 
            List.of(tag3, tag4)
        );
        contentRepository.save(content2);

        Content content3 = new Content(
            "Contenido C", 
            "Descripción C", 
            "Autor C", 
            "Fuente C", 
            "Español", 
            "https://example.com/c", 
            "https://example.com/images/c.jpg", 
            List.of(tag5, tag6)
        );
        contentRepository.save(content3);
    }
}
