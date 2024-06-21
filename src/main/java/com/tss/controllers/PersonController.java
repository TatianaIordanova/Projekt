package com.tss.controllers;

import com.tss.entities.Person;
import com.tss.repositories.PersonRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for displaying persons from persons table in JSON format 
 */
@RestController
@RequestMapping("/webresources")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/webservices")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
