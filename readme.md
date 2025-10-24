# API Gerenciador de Tarefas 📝

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-green?style=for-the-badge&logo=spring)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red?style=for-the-badge&logo=apache-maven)

Este projeto é uma API RESTful completa para o gerenciamento de tarefas, desenvolvida com Java 17 e Spring Boot. O objetivo principal é demonstrar a implementação de uma arquitetura de software robusta e desacoplada, seguindo as melhores práticas do mercado.

## 🏛️ Arquitetura e Boas Práticas

Este projeto foi estruturado para demonstrar conhecimento em conceitos-chave da engenharia de software, tornando-o um portfólio atraente para recrutadores.

### 1. Arquitetura em 3 Camadas

A aplicação é rigorosamente separada em três camadas lógicas para garantir a separação de responsabilidades (Separation of Concerns):

- **Camada de Controle (`@RestController`)**

  - Responsável por expor os endpoints da API, lidar com requisições e respostas HTTP.
  - Atua como "tradutor", convertendo Entidades para DTOs (na resposta) e DTOs para Entidades (na requisição).
  - Delega toda a lógica de negócio para a camada de serviço.

- **Camada de Serviço (`@Service`)**

  - O "cérebro" da aplicação. Contém toda a lógica de negócio (ex: validações, regras de criação).
  - Define valores padrão (ex: `setCompleted(false)` em novas tarefas).
  - Orquestra as operações, comunicando-se com a camada de repositório.
  - É totalmente independente do protocolo HTTP.

- **Camada de Repositório (`@Repository`)**
  - Camada de acesso a dados, utilizando `Spring Data JPA` para abstrair a comunicação com o banco de dados.
  - Define consultas personalizadas (ex: `findTaskByDescriton`).

### 2. Padrão DTO (Data Transfer Object)

Para garantir a segurança e a flexibilidade da API, utilizamos DTOs para separar o modelo interno da entidade (`Task`) do que é exposto ao cliente:

- **DTOs de Requisição (Request):** Contratos específicos para cada operação de entrada (`TaskCreateDTO`, `TaskUpdateDTO`, `TaskCompleteDTO`), garantindo que o cliente envie apenas os dados necessários.
- **DTOs de Resposta (Response):** Um `TaskResponseDTO` seguro é usado em todas as respostas, protegendo campos sensíveis da entidade e garantindo um contrato de API estável.

### 3. Tratamento de Exceções Centralizado

Implementamos um manipulador de exceções global para toda a aplicação, o que torna os controllers limpos e a API robusta:

- **`@RestControllerAdvice`:** Uma classe central ouve todas as exceções lançadas pela camada de serviço.
- **`@ExceptionHandler`:** Métodos específicos capturam exceções de negócio personalizadas e as traduzem em respostas HTTP claras e padronizadas.
- **Exceções Personalizadas:**
  - `TaskNotFoundException` ➡️ `404 NOT_FOUND`
  - `TaskSameDescriptionExistException` ➡️ `409 CONFLICT`
  - `TaskDescpritionNullOrEmpty` ➡️ `400 BAD_REQUEST`
  - `TasksEmptyException` ➡️ `204 NO_CONTENT`

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot** (Web, Data JPA)
- **Lombok**
- **Apache Maven**
- **H2 Database** (Banco de dados em memória)

## 🚀 Como Executar Localmente

### Pré-requisitos

- JDK 17 ou superior
- Apache Maven

### Passos

1.  **Clone o repositório:**

    ```bash
    git clone [https://github.com/seu-usuario/gerenciador-tarefas.git](https://github.com/seu-usuario/gerenciador-tarefas.git)
    ```

2.  **Navegue até o diretório do projeto:**

    ```bash
    cd gerenciador-tarefas
    ```

3.  **Execute a aplicação com o Maven Wrapper:**

    - No macOS/Linux: `./mvnw spring-boot:run`
    - No Windows: `mvnw.cmd spring-boot:run`

4.  **Acesse a API:**
    A aplicação estará disponível em `http://localhost:8080`.

### Acessando o Banco de Dados H2

Para visualizar os dados diretamente no banco, acesse o console do H2 no seu navegador:

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `user`
- **Password:** `password`

## 📖 Endpoints da API

Abaixo está a documentação dos endpoints disponíveis até o momento.

| Método   | URL               | Descrição                                     | Exemplo de Corpo (Body)                                                            | Resposta (Sucesso)                         |
| :------- | :---------------- | :-------------------------------------------- | :--------------------------------------------------------------------------------- | :----------------------------------------- |
| `POST`   | `/api`            | Cria uma nova tarefa.                         | `{"description": "Estudar DTOs", "priority": "Alta", "deadline": "Diário"}`        | `201 CREATED` (Retorna `TaskResponseDTO`)  |
| `GET`    | `/api`            | Lista todas as tarefas cadastradas.           | -                                                                                  | `200 OK` (Retorna `List<TaskResponseDTO>`) |
| `GET`    | `/api/tasks/{id}` | Busca uma tarefa específica pelo ID.          | -                                                                                  | `200 OK` (Retorna `TaskResponseDTO`)       |
| `PUT`    | `/api/tasks/{id}` | Atualiza todas as informações de uma tarefa.  | `{"description": "Estudar Mappers", "priority": "Urgente", "deadline": "Semanal"}` | `200 OK` (Retorna `TaskResponseDTO`)       |
| `PATCH`  | `/api/tasks/{id}` | Atualiza o status de conclusão de uma tarefa. | `{"completed": true}`                                                              | `200 OK` (Retorna `TaskResponseDTO`)       |
| `DELETE` | `/api/tasks/{id}` | Deleta uma tarefa específica pelo ID.         | -                                                                                  | `204 NO_CONTENT`                           |
