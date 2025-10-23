package br.com.gerenciador_tarefas.dto;

public record TaskCreateDTO(Long id, String description, String priority, String deadline) {
}
