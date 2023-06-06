package com.alibou.security.user;

import java.util.List;
import java.util.Optional;

import com.alibou.security.demo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

}
