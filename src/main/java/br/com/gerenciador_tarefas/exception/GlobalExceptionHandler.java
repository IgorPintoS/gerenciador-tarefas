package br.com.gerenciador_tarefas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage().trim());
    }

    @ExceptionHandler(TaskSameDescriptionExistException.class)
    public ResponseEntity<String> handleTaskSameDescription(TaskSameDescriptionExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage().trim());
    }

    @ExceptionHandler(TaskDescriptionNullOrEmpty.class)
    public ResponseEntity<String> handleTaskDescriptionNullOrEmpty(TaskDescriptionNullOrEmpty e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage().trim());
    }

    @ExceptionHandler(TasksEmptyException.class)
    public ResponseEntity<String> handleTasksEmpty(TasksEmptyException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage().trim());
    }
}
