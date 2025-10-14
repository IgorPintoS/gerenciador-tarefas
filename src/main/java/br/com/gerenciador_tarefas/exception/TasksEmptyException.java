package br.com.gerenciador_tarefas.exception;

public class TasksEmptyException extends RuntimeException {
    public TasksEmptyException(String message) {
        super(message);
    }
}
