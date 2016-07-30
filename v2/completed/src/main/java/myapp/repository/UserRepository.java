package myapp.repository;

import myapp.entity.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

User findByUserName(String UserName);
}
