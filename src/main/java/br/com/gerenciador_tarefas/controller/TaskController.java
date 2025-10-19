package br.com.gerenciador_tarefas.controller;

import br.com.gerenciador_tarefas.model.Task;
import br.com.gerenciador_tarefas.repository.TaskRepository;
import br.com.gerenciador_tarefas.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService; //injeção de dependencia via construtor, boa prática -> delegando agora para camada services
    }
    //simplificando os métodos, delegando para o services.

    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        List<Task> taskList = taskService.findAllTasks(); //retorna a lista de tasks.

        return ResponseEntity.ok(taskList); //senão retorna o HTTP 200 junto com a lista.
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) { //anotação lincando com o {id}
        Task taskOptional = taskService.findTaskById(id); //buscando pelo Id, sempre Optional

        return ResponseEntity.ok(taskOptional); //senão retorna HTTP 200 junto com objeto task
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) { //anotação informando que é necessário um corpo na requisição
        Task createdTask = taskService.createNewTask(task); //salvando a task no bd

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED); //retornando um created, com os dados
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskUpdated) { //anotação lincando com o {id}, e dizendo que é necessário um corpo com os novos dados
        Task updatedTask = taskService.updateExistsTask(id, taskUpdated);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) { //anotação lincando com o {id}
        taskService.deleteTaskById(id); //deletando pelo id

        return ResponseEntity.noContent().build(); //retornando um no content pois o registro não existe mais, boa prática
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> situationUpdateTask(@PathVariable Long id, @RequestBody Task situationUpdated) {
        Task taskCompleted = taskService.completeTask(id, situationUpdated);

        return ResponseEntity.ok(taskCompleted);
    }
}
