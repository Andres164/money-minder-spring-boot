package com.baio.money_minder.controllers;

import com.baio.money_minder.entities.Tag;
import com.baio.money_minder.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagRepository tagRepository;


    // get
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable(name = "id") Long id){
        var tag = this.tagRepository.findById(id).orElse(null);
        if (tag == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tag);
    }

    // getAll
    @GetMapping
    public ResponseEntity<Iterable<Tag>> getAllTags(){
        var tags = this.tagRepository.findAll();

        return ResponseEntity.ok(tags);
    }

    // create
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag, UriComponentsBuilder uriBuilder){
        boolean isTagDuplicate = this.tagRepository.existsTagByName(tag.getName());
        if (isTagDuplicate){
            //TODO: Terminar y configurar error personalizado
            return ResponseEntity.badRequest().build();
        }
        this.tagRepository.save(tag);
        var taguri = uriBuilder.path("/tags/{id}").buildAndExpand(tag.getId()).toUri();
        return ResponseEntity.created(taguri).build();
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable(name = "id") Long id, @RequestBody Tag request){
        var tag = this.tagRepository.findById(id).orElse(null);
        if (tag==null){
            return ResponseEntity.notFound().build();
        }
        tag.setName(request.getName());
        this.tagRepository.save(tag);
        return  ResponseEntity.ok(tag);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable(name = "id") Long id){
        var tag = this.tagRepository.findById(id).orElse(null);
        if (tag==null){
            return ResponseEntity.notFound().build();
        }
        this.tagRepository.delete(tag);
        return ResponseEntity.noContent().build();
    }

}
