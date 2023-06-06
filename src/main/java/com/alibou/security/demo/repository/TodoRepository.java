package com.alibou.security.demo.repository;

import com.alibou.security.demo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository  extends JpaRepository<Todo, Integer> {

   List<Todo> findByUsername(String username);

   List<Todo> findByUsernameAndDateBetween(String username, LocalDate startOfMonth, LocalDate endOfMonth);

}
