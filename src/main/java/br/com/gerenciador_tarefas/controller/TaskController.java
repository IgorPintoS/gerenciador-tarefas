package br.com.gerenciador_tarefas.controller;

import br.com.gerenciador_tarefas.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> taskList = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Task> getTaskList() {
        return taskList;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setId(counter.getAndIncrement());
        taskList.add(task);
        return task;
    }
}
