package br.com.gerenciador_tarefas.service;

import br.com.gerenciador_tarefas.exception.TaskNotFoundException;
import br.com.gerenciador_tarefas.exception.TasksEmptyException;
import br.com.gerenciador_tarefas.model.Task;
import br.com.gerenciador_tarefas.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        List<Task> taskList = taskRepository.findAll();

        if(taskList.isEmpty()) {
            throw new TasksEmptyException("There's no tasks here.");
        }
        return taskList;
    }

    public Optional<Task> findTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty()) {
            throw new TaskNotFoundException("Task id: " + id + "don't exists.");
        }

        return taskOptional;
    }
}
