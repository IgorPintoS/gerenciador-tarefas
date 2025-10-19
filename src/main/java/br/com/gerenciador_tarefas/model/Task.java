package br.com.gerenciador_tarefas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Anotação do Lombok que cria getters, setters, toString, etc. automaticamente.
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String priority;
    private Boolean completed; //alterado para permitir null

    //overload no get, para permitir verificar se o dado é diferente de null no controller
    public Boolean getCompleted() {
        return completed;
    }
}
