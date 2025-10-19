package br.com.gerenciador_tarefas.exception;

public class TaskDescpritionNullOrEmpty extends RuntimeException {
    public TaskDescpritionNullOrEmpty(String message) {
        super(message);
    }
}
