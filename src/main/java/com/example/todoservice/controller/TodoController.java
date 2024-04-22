package com.example.todoservice.controller;

import com.example.todoservice.entity.Todo;
import com.example.todoservice.exception.TodoNotFoundException;
import com.example.todoservice.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${cors.urls}")
@RestController
public class TodoController {
    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/getTodoList")
    public List<Todo> getTodoList() {
        return todoRepository.findAll();
    }

    @PostMapping("/addTodo")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @GetMapping("/getTodo/{id}")
    public Todo getTodo(@PathVariable Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id) );
    }

    @PutMapping("/updateTodo/{id}")
    public Todo updateTodo(@RequestBody Todo newTodo, @PathVariable Long id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTask(newTodo.getTask());
                    todo.setComplete(newTodo.isComplete());
                    return todoRepository.save(todo);
                }).orElseGet(() -> {
                    newTodo.setId(id);
                    return todoRepository.save(newTodo);
                });
    }

    @DeleteMapping("/getTodo/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
