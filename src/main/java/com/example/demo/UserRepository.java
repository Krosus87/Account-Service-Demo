package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<Person, Long> {
    Optional<Person> findUserByUsernameIgnoreCase(String username);

    List<Person> findAll();
}
