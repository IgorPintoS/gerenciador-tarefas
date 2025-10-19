package br.com.gerenciador_tarefas.exception;

public class TaskSameDescriptionExistException extends RuntimeException {
    public TaskSameDescriptionExistException(String message) {
        super(message);
    }
}
