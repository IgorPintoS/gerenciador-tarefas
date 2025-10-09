package br.com.gerenciador_tarefas.repository;

import br.com.gerenciador_tarefas.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
