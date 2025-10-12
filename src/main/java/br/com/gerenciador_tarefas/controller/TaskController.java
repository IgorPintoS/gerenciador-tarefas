package br.com.gerenciador_tarefas.controller;

import br.com.gerenciador_tarefas.model.Task;
import br.com.gerenciador_tarefas.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository; //injeção de dependencia via construtor, boa prática
    }
    //é uma boa prática retornar nos métodos controllers um ResponseEntity personalizavel ao invés de delegar para o spring

    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        List<Task> taskList = taskRepository.findAll(); //retorna a lista de tasks.

        if(taskList.isEmpty()) { //verifica se não está vazia, estando dispara um HTTP 204, retornou uma lista vazia.
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(taskList); //senão retorna o HTTP 200 junto com a lista.
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long id) { //anotação lincando com o {id}
        Optional<Task> taskOptional = taskRepository.findById(id); //buscando pelo Id, sempre Optional

        if(taskOptional.isEmpty()) { //verificando se está vazio, retornar not found 404
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskOptional.get()); //senão retorna HTTP 200 junto com objeto task
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) { //anotação informando que é necessário um corpo na requisição
        Task createdTask = taskRepository.save(task); //salvando a task no bd

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED); //retornando um created, com os dados
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskUpdated) { //anotação lincando com o {id}, e dizendo que é necessário um corpo com os novos dados
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty()) { //verificando se o id existe
            return ResponseEntity.notFound().build();
        }

        taskOptional.get().setDescription(taskUpdated.getDescription()); //setando os campos alterado ou não.
        taskOptional.get().setPriority(taskUpdated.getPriority());
        Task taskSaved = taskRepository.save(taskOptional.get()); // salvando a nova task

        return ResponseEntity.ok(taskSaved);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) { //anotação lincando com o {id}
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty()) { //verificando se o id existe
            return ResponseEntity.notFound().build();
        }

        taskRepository.deleteById(id); //deletando pelo id

        return ResponseEntity.noContent().build(); //retornando um no content pois o registro não existe mais, boa prática
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> situationUpdateTask(@PathVariable Long id, @RequestBody Task situationUpdated) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if(taskOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (situationUpdated.getCompleted() != null) {
            taskOptional.get().setCompleted(situationUpdated.getCompleted());
        }

        Task taskComplete = taskRepository.save(taskOptional.get());

        return ResponseEntity.ok(taskComplete);
    }
}
