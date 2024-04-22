package com.example.todoservice.repository;

import com.example.todoservice.entity.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    private TodoRepository todoRepository;

    @Autowired
    public DataLoader(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void run(ApplicationArguments args) throws Exception {
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(Paths.get("src/main/resources/data.json").toFile(), Object.class);
        String jsonInString = new Gson().toJson(obj);
        List<Todo> todoList = Arrays.asList(gson.fromJson(jsonInString, Todo[].class));
        todoRepository.saveAll(todoList);
    }
}
