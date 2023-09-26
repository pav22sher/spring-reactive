package com.example.springreactive.controller;


import com.example.springreactive.dto.EmployeeDto;
import com.example.springreactive.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getById(@PathVariable("id") String id){
        return employeeService.getById(id);
    }

    @GetMapping
    public Flux<EmployeeDto> getAll(){
        return employeeService.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> save(@RequestBody EmployeeDto dto){
        return employeeService.save(dto);
    }

    @PutMapping
    public Mono<EmployeeDto> update(@RequestBody EmployeeDto dto){
        return employeeService.update(dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") String id){
        return employeeService.delete(id);
    }
}