package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
// MongoRepository gives you save(), findAll(), findById(), deleteById() out of the box!
public interface UserRepository extends MongoRepository<User, String> {
boolean existsByEmail(String email);
}
