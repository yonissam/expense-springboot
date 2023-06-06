package com.alibou.security.demo;

import com.alibou.security.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

  private TodoRepository todoRepository;

  public DemoController(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @GetMapping("/demo-controller")
  public String sayHello() {

    return "Hello from secured endpoint at lion of judah";
  }

  @GetMapping("/users/{username}/todos/all")
  public List<Todo> retrieveAllUsernameTodos(@PathVariable String username) {
    return todoRepository.findByUsername(username);
  }

  @GetMapping("/users/{username}/todos")
  public List<Todo> retrieveUsernameTodosMonthly(@PathVariable String username) {
    LocalDate currentDate = LocalDate.now();
    LocalDate startOfMonth = YearMonth.from(currentDate).atDay(1);
    LocalDate endOfMonth = YearMonth.from(currentDate).atEndOfMonth();

    return todoRepository.findByUsernameAndDateBetween(username, startOfMonth, endOfMonth);
  }

  @GetMapping("/users/{username}/todos/{id}")
  public Todo retrieveTodoByUsernameAndId(@PathVariable String username, @PathVariable int id) {
    return todoRepository.findById(id).get();
  }

  @DeleteMapping("/users/{username}/todos/{id}")
  public ResponseEntity<Void> deleteTodoByUsernameAndId(@PathVariable String username, @PathVariable int id) {
    todoRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/users/{username}/todos/{id}")
  public Todo updateTodoByUsernameAndId(@PathVariable String username, @PathVariable int id, @RequestBody Todo todo) {

    todoRepository.save(todo);
    return todo;
  }

  @PostMapping("/users/{username}/todos")
  public Todo createTodosByUsername(@PathVariable String username,  @RequestBody Todo todo) {
    todo.setUsername(username);
    todo.setId(null);
    return  todoRepository.save(todo);

  }

}
