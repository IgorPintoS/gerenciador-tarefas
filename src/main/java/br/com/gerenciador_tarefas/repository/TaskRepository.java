package br.com.gerenciador_tarefas.repository;

import br.com.gerenciador_tarefas.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findTaskByDescriton(String description);
}
