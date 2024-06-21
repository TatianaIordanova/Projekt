package com.tss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tss.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
