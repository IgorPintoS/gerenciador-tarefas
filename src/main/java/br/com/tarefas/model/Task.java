package br.com.tarefas.model;

import lombok.Data;

@Data // Anotação do Lombok que cria getters, setters, toString, etc. automaticamente.
public class Task {
    private Long id;
    private String description;
    private String priority;
    private boolean completed;
}
