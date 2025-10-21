package br.com.gerenciador_tarefas.service;

import br.com.gerenciador_tarefas.exception.TaskDescriptionNullOrEmpty;
import br.com.gerenciador_tarefas.exception.TaskNotFoundException;
import br.com.gerenciador_tarefas.exception.TaskSameDescriptionExistException;
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

    public Task findTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty()) {
            throw new TaskNotFoundException("Task id: " + id + "don't exists.");
        }

        return taskOptional.get();
    }

    public Task createNewTask(Task newTask) {
        if(newTask.getDescription() == null || newTask.getDescription().isEmpty()) {
            throw new TaskDescriptionNullOrEmpty("Task description is empty.");
        }

        Optional<Task> taskOptional = taskRepository.findTaskByDescriton(newTask.getDescription());

        if(taskOptional.isPresent()) {
            throw new TaskSameDescriptionExistException("A task with this description already exists.");
        }

        return taskRepository.save(newTask);
    }
    //Usando o método da classe, pois este busca no banco e lança a exception, então não preciso utilizar o findById novamente.
    public Task updateExistsTask(Long id, Task updatedTask) {
        Task taskUpdate = this.findTaskById(id);

        taskUpdate.setDescription(updatedTask.getDescription());
        taskUpdate.setPriority(updatedTask.getPriority());

        return taskRepository.save(taskUpdate);
    }

    public void deleteTaskById(Long id) {
        Task taskDelete = this.findTaskById(id);

        taskRepository.delete(taskDelete);
    }

    public Task completeTask(Long id, Task completedTask) {
        Task taskToUpdate = this.findTaskById(id);

        if(completedTask.getCompleted() != null) {
            taskToUpdate.setCompleted(completedTask.getCompleted());
        }

        return taskRepository.save(taskToUpdate);
    }
}
