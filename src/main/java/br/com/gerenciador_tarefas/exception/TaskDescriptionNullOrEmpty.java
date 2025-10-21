package br.com.gerenciador_tarefas.exception;

public class TaskDescriptionNullOrEmpty extends RuntimeException {
    public TaskDescriptionNullOrEmpty(String message) {
        super(message);
    }
}
