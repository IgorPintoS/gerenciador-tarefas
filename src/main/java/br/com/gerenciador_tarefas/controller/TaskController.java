package br.com.gerenciador_tarefas.controller;

import br.com.gerenciador_tarefas.model.Task;
import br.com.gerenciador_tarefas.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

//    private final List<Task> taskList = new ArrayList<>();
//    private final AtomicLong counter = new AtomicLong();

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

//    @GetMapping
//    public List<Task> getTaskList() {
//        return taskList;
//    }
//
//    @PostMapping
//    public Task createTask(@RequestBody Task task) {
//        task.setId(counter.getAndIncrement());
//        taskList.add(task);
//        return task;
//    }
    @GetMapping
    public List<Task> findAll() {
        List<Task> taskList;
        try {
            taskList = taskRepository.findAll();
        } catch (NullPointerException e) {
            e.getStackTrace();
            return null;
        }
        return taskList;
    }
    @PostMapping
    public void saveTask(@RequestBody Task task) {
        try {
            task.setDescription(task.getDescription());
            task.setPriority(task.getPriority());
            taskRepository.save(task);
        } catch (NullPointerException e) {
            e.getStackTrace();
        }

        System.out.println("Task createad.");
    }
}
