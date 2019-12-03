package com.example.demo.controller;

import com.example.demo.domain.Language;
import com.example.demo.repo.LanguageRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(produces = "application/json")
public class LanguageController {

    private LanguageRepo languageRepo;

    public LanguageController(LanguageRepo languageRepo) {
        this.languageRepo = languageRepo;
    }


    /**
     * если обьектов много то ми оборачиваем их во Flux<Language>
     * @return
     */
    @GetMapping
    public Flux<Language> index(){
        return languageRepo.findAll();
    }

    /**
     * если обьект один то ми оборачиваем Mono<Language>
     */
    @GetMapping("/{name}")
    public Mono<Language> show(@PathVariable("name") String name){
        return languageRepo.findByName(name);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Language> create(@RequestBody Mono<Language> languageMono){
        return languageRepo.saveAll(languageMono).next();
    }

    @DeleteMapping("/{name}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("name") String name){
        return languageRepo.findByName(name)
                .flatMap(existingLanguage -> languageRepo.delete(existingLanguage)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{name}")
    public Mono<ResponseEntity<Language>> update(@PathVariable("name") String name,
                                                 @RequestBody Language language){
        return languageRepo.findByName(name)
                .flatMap(existingLanguage -> {
                    if (language.getCreator() != null) existingLanguage.setCreator(language.getCreator());
                    if (language.getFeature() != null) existingLanguage.setFeature(language.getFeature());
                    return languageRepo.save(existingLanguage);
                }).map(updatedLanguage -> new ResponseEntity<>(updatedLanguage,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
