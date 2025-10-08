package br.com.tarefas.gerenciador_tarefas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 1. Anotação que indica que esta é uma classe de controle REST
@RequestMapping("/api/hello") // 2. Mapeia todas as requisições que começam com /api/hello para este controller
public class HelloController {

    @GetMapping // 3. Mapeia requisições HTTP GET para este método
    public String sayHello() {
        return "Olá Spring Boot!";
    }
}
