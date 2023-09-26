package com.example.springreactive.controller;

import com.example.springreactive.dto.EmployeeDto;
import com.example.springreactive.repository.EmployeeRepository;
import com.example.springreactive.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void before() {
        employeeRepository.deleteAll().subscribe();
    }

    @Test
    public void testSave() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("John");
        dto.setLastName("Cena");
        dto.setEmail("john@gmail.com");
        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(dto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(dto.getLastName())
                .jsonPath("$.email").isEqualTo(dto.getEmail());
    }

    @Test
    public void testGetById() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Meena");
        dto.setLastName("Fadatare");
        dto.setEmail("meena@gmail.com");
        EmployeeDto entity = employeeService.save(dto).block();
        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", entity.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(entity.getId())
                .jsonPath("$.firstName").isEqualTo(dto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(dto.getLastName())
                .jsonPath("$.email").isEqualTo(dto.getEmail());
    }

    @Test
    public void testGetAll() {
        EmployeeDto dto1 = new EmployeeDto();
        dto1.setFirstName("John");
        dto1.setLastName("Cena");
        dto1.setEmail("john@gmail.com");
        employeeService.save(dto1).block();
        EmployeeDto dto2 = new EmployeeDto();
        dto2.setFirstName("Meena");
        dto2.setLastName("Fadatare");
        dto2.setEmail("meena@gmail.com");
        employeeService.save(dto2).block();
        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdate() {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Ramesh");
        dto.setLastName("Fadatare");
        dto.setEmail("ramesh@gmail.com");
        EmployeeDto entity = employeeService.save(dto).block();
        EmployeeDto udtDto = new EmployeeDto();
        udtDto.setId(entity.getId());
        udtDto.setFirstName("Ram");
        udtDto.setLastName("Jadhav");
        udtDto.setEmail("ram@gmail.com");
        webTestClient.put().uri("/api/employees", Collections.singletonMap("id", entity.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(udtDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(udtDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(udtDto.getLastName())
                .jsonPath("$.email").isEqualTo(udtDto.getEmail());
    }

    @Test
    public void testDelete() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Ramesh");
        employeeDto.setLastName("Fadatare");
        employeeDto.setEmail("ramesh@gmail.com");
        EmployeeDto savedEmployee = employeeService.save(employeeDto).block();
        webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}