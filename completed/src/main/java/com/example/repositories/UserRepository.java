package com.example.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByUserName(String name);
    User findOneByEmail(String email);
    User findOneByUserNameOrEmail(String username, String email);
}