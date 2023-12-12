package dev.learn.simpleuserlogin.repository;

import dev.learn.simpleuserlogin.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
