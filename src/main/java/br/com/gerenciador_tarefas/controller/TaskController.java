package br.com.gerenciador_tarefas.controller;

import br.com.gerenciador_tarefas.dto.TaskCompleteDTO;
import br.com.gerenciador_tarefas.dto.TaskCreateDTO;
import br.com.gerenciador_tarefas.dto.TaskResponseDTO;
import br.com.gerenciador_tarefas.dto.TaskUpdateDTO;
import br.com.gerenciador_tarefas.model.Task;
import br.com.gerenciador_tarefas.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService; //injeção de dependencia via construtor, boa prática -> delegando agora para camada services
    }
    //simplificando os métodos, delegando para o services.

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        List<Task> taskList = taskService.findAllTasks(); //retorna a lista de tasks.
        List<TaskResponseDTO> taskResponseDTOList = new ArrayList<>();

        for (Task task : taskList) { //refatorar para stream()
            TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task.getId(),
                    task.getDescription(),
                    task.getPriority(),
                    task.getCompleted(),
                    task.getDeadline());

            taskResponseDTOList.add(taskResponseDTO);
        }

        return ResponseEntity.ok(taskResponseDTOList); //retorna o HTTP 200 junto com a lista.
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id) { //anotação lincando com o {id}
        Task task = taskService.findTaskById(id); //buscando pelo Id

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(task.getId(),
                task.getDescription(),
                task.getPriority(),
                task.getCompleted(),
                task.getDeadline());

        return ResponseEntity.ok(taskResponseDTO); //retorna HTTP 200 junto com objeto task
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskCreateDTO taskCreateDTO) { //anotação informando que é necessário um corpo na requisição
        Task createdTask = new Task(); //refatorado para utilizar o DTO, e não expor dados ao cliente
        createdTask.setId(taskCreateDTO.id());
        createdTask.setDescription(taskCreateDTO.description());
        createdTask.setPriority(taskCreateDTO.priority());
        createdTask.setDeadline(taskCreateDTO.deadline());

        Task task = taskService.createNewTask(createdTask); //salvando a task no bd

        TaskResponseDTO taskDTO = new TaskResponseDTO(task.getId(),
                task.getDescription(),
                task.getPriority(),
                task.getCompleted(),
                task.getDeadline());

        return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO); //retornando um created, com os dados
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskUpdatedDTO) { //anotação lincando com o {id}, e dizendo que é necessário um corpo com os novos dados
        Task updatedTask = new Task();
        updatedTask.setDescription(taskUpdatedDTO.description());
        updatedTask.setPriority(taskUpdatedDTO.priority());
        updatedTask.setDeadline(taskUpdatedDTO.deadline());

        Task task = taskService.updateExistsTask(id, updatedTask);

        TaskResponseDTO taskDTO = new TaskResponseDTO(task.getId(),
                task.getDescription(),
                task.getPriority(),
                task.getCompleted(),
                task.getDeadline());

        return ResponseEntity.ok(taskDTO);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) { //anotação lincando com o {id}
        taskService.deleteTaskById(id); //deletando pelo id

        return ResponseEntity.noContent().build(); //retornando um no content pois o registro não existe mais, boa prática
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> situationUpdateTask(@PathVariable Long id, @RequestBody TaskCompleteDTO situationUpdatedDTO) {
        Task taskCompleted = new Task();
        taskCompleted.setCompleted(situationUpdatedDTO.completed());

        Task task = taskService.completeTask(id, taskCompleted);

        TaskResponseDTO taskDTO = new TaskResponseDTO(task.getId(),
                task.getDescription(),
                task.getPriority(),
                task.getCompleted(),
                task.getDeadline());

        return ResponseEntity.ok(taskDTO);
    }
}
