package br.com.gerenciador_tarefas.dto;

public record TaskResponseDTO(Long id, String description, String priority, Boolean completed, String deadline) {
}
